package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SimpleServer extends AbstractServer {
    private static Session session;
    private static List<ClinicEntity> Clinics;

    public void initSesssion() {
        session = getSessionFactory().openSession();
        try {
            String[] service = new String[]{"aaa", "bbb", "ccc"};
            ClinicEntity clinic1 = new ClinicEntity("Haifa clinic", "10:00", "20:00", service, new ArrayList<PatientEntity>());
            session.save(clinic1);
            service = new String[]{"bbb", "ddd", "ccc"};
            ClinicEntity clinic2 = new ClinicEntity("Acre clinic", "12:00", "18:00", service, new ArrayList<PatientEntity>());
            session.save(clinic2);
            service = new String[]{"aaa", "eee", "ddd"};
            ClinicEntity clinic3 = new ClinicEntity("Tel-Aviv clinic", "14:00", "22:00", service, new ArrayList<PatientEntity>());
            session.save(clinic3);
            service = new String[]{"ww", "zz"};
            ClinicEntity clinic4 = new ClinicEntity("Eilaboun clinic", "08:00", "12:00", service, new ArrayList<PatientEntity>());
            session.save(clinic4);
            PatientEntity pat1 = new PatientEntity(318588324,"Emad","daraw","Emad123@gmail.xom","Em12345",23,clinic1);
            session.save(pat1);
            PatientEntity pat2 = new PatientEntity(318234732,"Khaled","Sger","Khaled123@gmail.com","Kh12345",23,clinic4);
            session.save(pat2);
            NurseEntity nurse1 = new NurseEntity(792596666,"Good","Nurse","nursegood@gmail.com","Goo123",clinic1);
            session.save(nurse1);
            DoctorEntity doc1= new DoctorEntity(2113423,"dr","fischer","drfischer@gmail.com","111","Neurology");
            session.save(doc1);
            ArrayList<String> times=new ArrayList<String>();
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("00:00-00:00");
            times.add("00:00-00:00");


            DoctorClinicEntity doctorClinic= new DoctorClinicEntity(doc1,clinic3,times);
            session.save(doctorClinic);


            ManagerEntity manger = new ManagerEntity(doc1.getId(), doc1.getFirst_name(), doc1.getFamily_name(),
                    doc1.getMail(),"111",clinic2);
            session.save(manger);
            DoctorPatientEntity docpat=new DoctorPatientEntity(doc1,pat1);
            session.save(docpat);

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {

                session.getTransaction().rollback();
            }
        }
    }

    public SimpleServer(int port) {
        super(port);
        initSesssion();
    }

    public void stopSession() {
        if (session != null) {
            session.close();
        }
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ClinicEntity.class);
        configuration.addAnnotatedClass(PatientEntity.class);
        configuration.addAnnotatedClass(NurseEntity.class);
        configuration.addAnnotatedClass(DoctorEntity.class);
        configuration.addAnnotatedClass(DoctorClinicEntity.class);
        configuration.addAnnotatedClass(ManagerEntity.class);
        configuration.addAnnotatedClass(DoctorPatientEntity.class);
        configuration.addAnnotatedClass(LabWorkerEntity.class);
        configuration.addAnnotatedClass(AppointmentEntity.class);



        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        String msgString = msg.toString();
        if (msgString.startsWith("#warning")) {
            Warning warning = new Warning("Warning from server!");
            try {
                client.sendToClient(warning);
                System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msgString.startsWith("#CloseSession")) {
            stopSession();
        } else if (msgString.equals("#GetAllClinics")) {
            try {
                //UpdateAppointments();

                List<ClinicEntity> clinics = getALLClinics();
                Clinics = clinics;
                client.sendToClient(clinics);
                System.out.format("Sent all clinics to client %s\n", client.getInetAddress().getHostAddress());
            } catch (Exception e) {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            }
        } else if (msg.getClass().equals(ClinicEntity.class)) {
            for (int i = 0; i < Clinics.size(); i++) {
                if (Clinics.get(i).getId() == ((ClinicEntity) msg).getId()) {
                    session.beginTransaction();
                    Clinics.get(i).setOpen(((ClinicEntity) msg).getOpen());
                    Clinics.get(i).setClose(((ClinicEntity) msg).getClose());
                    session.save(Clinics.get(i));
                    session.flush();
                    session.getTransaction().commit();
                    System.out.format("Updating all clinics on client %s\n", client.getInetAddress().getHostAddress());
                }
            }
        } else if (msg.getClass().equals(UserEntity.class)){
            List<ManagerEntity> Mangers = getALLMangers();
           boolean flag_manager = checkPassword(Mangers,((UserEntity) msg),client);
            List<DoctorEntity> Doctors = getALLDoctors();
            boolean flag_doctor = checkPassword(Doctors,((UserEntity) msg),client);
            List<NurseEntity> Nurses = getALLNurses();
            boolean flag_nurse = checkPassword(Nurses,((UserEntity) msg),client);
            List<PatientEntity> Patients = getALLPatients();
            boolean flag_patient = checkPassword(Patients,((UserEntity) msg),client);
            String stringResult="";
            if(flag_manager||flag_doctor||flag_patient||flag_nurse){
                stringResult="#Login Success";
            }else{
                stringResult="#Login Failure";
            }
            try {
                client.sendToClient(stringResult);

            }catch (Exception e) {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            }

        }
    }
    <T extends UserEntity> boolean checkPassword(List<T> Users,UserEntity user,ConnectionToClient client){
        for (int i = 0 ; i < Users.size(); i++){
            if(user.getId() == Users.get(i).getId()){
                if(Users.get(i).comparePassword(user.getPassword())) {
                    try {
                        client.sendToClient(Users.get(i));
                        return true;
                    } catch (IOException e) {
                        if (session != null) {
                            session.getTransaction().rollback();
                        }
                    }
                }else{
                    return false;
                }
            }
        }
        return  false;
    }
    private static void UpdateAppointments(){
        LocalDateTime now=LocalDateTime.now();
        now.getDayOfWeek();
        List<DoctorEntity> all_docs=getALLDoctors();
        for (DoctorEntity doc:all_docs) {
            List<DoctorClinicEntity> doc_clinics = doc.getDoctorClinicEntities();
            List<AppointmentEntity> doc_appointments = doc.getAppointments();
            for (AppointmentEntity app:doc_appointments) {

                if(app.getDate().isBefore(now)) {
                    doc_appointments.remove(app);
                }
            }
            LocalDateTime latest_appointment;
            if(doc_appointments.size()>0)
            {
                latest_appointment=doc_appointments.get(doc_appointments.size()-1).getDate();
            }else{
                latest_appointment = now;
            }
            for (int i = latest_appointment.getMonthValue() ; i <= (now.getMonthValue()+3); i++)
            {
                int year= now.getYear();
                if(i>12){
                    year++;
                }
               /* Calendar cal = Calendar.getInstance();
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);*/
                YearMonth yearMonthObject = YearMonth.of(year, i);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                for (int j=1;j<=daysInMonth;j++){
                    for(DoctorClinicEntity doc_clinic : doc_clinics){
                        List<LocalTime> work_hours = doc_clinic.GetWorkingDateTime();
                        LocalDate localDate=LocalDate.of(year,i,j);
                        int day_of_week= getDayNumberNew(localDate);
                        if(day_of_week==7){
                            day_of_week=1;
                        }else{
                            day_of_week++;
                        }
                        LocalTime opening = work_hours.get(2*(day_of_week-1));
                        LocalTime closing =work_hours.get(2*(day_of_week-1)+1);

                        if(!opening.toString().equals("00:00") && !closing.toString().equals("00:00")){
                            for(int hour=opening.getHour()*60;hour<=closing.getHour()*60;hour+=20) {

                                LocalDateTime appointment_time = LocalDateTime.of(year, i % 12, j, hour / 60, hour % 60);
                                if (!appointment_time.isBefore(now)) {
                                    AppointmentEntity app = new AppointmentEntity(appointment_time, doc_clinic, 20);
                                    System.out.println(app.getDate().toString());
                                    doc.getAppointments().add(app);

                                }
                            }
                        }



                    }
                }
            }


        }
    }
    public static int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return (day.getValue());
    }


    private static List<ClinicEntity> getALLClinics() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClinicEntity> query = builder.createQuery(ClinicEntity.class);
        query.from(ClinicEntity.class);
        List<ClinicEntity> result = session.createQuery(query).getResultList();
        return result;
    }

    private static List<PatientEntity> getALLPatients() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PatientEntity> query = builder.createQuery(PatientEntity.class);
        query.from(PatientEntity.class);
        List<PatientEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<NurseEntity> getALLNurses() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<NurseEntity> query = builder.createQuery(NurseEntity.class);
        query.from(NurseEntity.class);
        List<NurseEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<DoctorEntity> getALLDoctors() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DoctorEntity> query = builder.createQuery(DoctorEntity.class);
        query.from(DoctorEntity.class);
        List<DoctorEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<ManagerEntity> getALLMangers() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ManagerEntity> query = builder.createQuery(ManagerEntity.class);
        query.from(ManagerEntity.class);
        List<ManagerEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<DoctorClinicEntity> getALLDoctorClinics() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DoctorClinicEntity> query = builder.createQuery(DoctorClinicEntity.class);
        query.from(DoctorClinicEntity.class);
        List<DoctorClinicEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<LabWorkerEntity> getALLLabWorkers() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<LabWorkerEntity> query = builder.createQuery(LabWorkerEntity.class);
        query.from(LabWorkerEntity.class);
        List<LabWorkerEntity> result = session.createQuery(query).getResultList();
        return result;
    }
}
