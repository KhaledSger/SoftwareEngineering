package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.*;
import java.util.*;


public class SimpleServer extends AbstractServer {
    public static Session session;
    private static List<ClinicEntity> Clinics;
    private static List<PatientEntity> Patients ;
    private static List<ManagerEntity> managers;
    public static ArrayList<AppointmentEntity> Appointments=new ArrayList<AppointmentEntity>();
    public static ArrayList<VaccineAppointmentEntity> vaccine_Appointments=new ArrayList<VaccineAppointmentEntity>();


    public void initSesssion() {
        session = getSessionFactory().openSession();
        executeSqlScript(new File("C:\\Users\\basel-2020-pc\\IdeaProjects\\SoftwareEngineering\\server\\src\\main\\resources\\query1.sql"));
        UpdateAppointments();
        Appointments = (ArrayList<AppointmentEntity>) GetAllAppointments();
        UpdateVaccineAppointments();


       /* session = getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
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
            PatientEntity pat1 = new PatientEntity(318588324,"Emad","daraw","Emad123@gmail.com","Em12345",23,clinic3);
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
                    doc1.getMail(),"111",clinic3);
            session.save(manger);
            DoctorPatientEntity docpat=new DoctorPatientEntity(doc1,pat1);
            session.save(docpat);
            session.flush();
            session.getTransaction().commit();
            UpdateAppointments();
            Appointments = (ArrayList<AppointmentEntity>) GetAllAppointments();
            UpdateVaccineAppointments();
           // vaccine_Appointments = (ArrayList<VaccineAppointmentEntity>) GetAllVaccineAppointments();
            System.out.println("appointments size"+Appointments.size());
           // myThread.start();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        }*/

        //Appointments = (ArrayList<AppointmentEntity>) GetAllAppointments();
    }

    public SimpleServer(int port) {
        super(port);
        initSesssion();
        MyThread myThread = new MyThread();
        myThread.start();
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
        configuration.addAnnotatedClass(VaccineAppointmentEntity.class);



        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        String msgString = msg.toString();
        System.out.println("msg: "+msgString);
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
        }
        else if(msgString.equals("#getAllPatients"))
        {
            List<PatientEntity> patients = getALLPatients();
            Patients = patients;
            System.out.println("pateint-size= " + Patients.size());
            try {
                client.sendToClient(Patients);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(msgString.startsWith("#getPatientApps:"))
        {
            msgString=msgString.substring(16);
            try {
            ArrayList<AppointmentEntity> patient_apps=get_apps_with_PatientId((Integer.parseInt(msgString)));
            System.out.println("patient apps size= "+patient_apps.size());

                client.sendToClient(patient_apps);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(msgString.equals("#getAllManagers"))
        {
            managers = getALLMangers();
            try {
                client.sendToClient(managers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (msgString.equals("#GetAllClinics")) {
            try {
                //UpdateAppointments();


                List<ClinicEntity> clinics = getALLClinics();
                Clinics = clinics;
                System.out.println("clinics size in server "+clinics.size());
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
        }
        else if (msg.getClass().equals(AppointmentEntity.class))
        {
            System.out.println("msg id "+((AppointmentEntity) msg).getId());
            AppointmentEntity app=get_app_with_id(((AppointmentEntity) msg).getId());
            System.out.println(app.getDate());
            if(!((AppointmentEntity) msg).isReserved()) // the client has pressed on app but not confirmed the reservation yet
            {
                app.setReserved(true);

            }
            else if((app.getPatient()==null) ) { // the client has confirmed the reservation
                app.setPatient(((AppointmentEntity) msg).getPatient());
                try {
                    client.sendToClient("reservation done!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((AppointmentEntity) msg).getPatient().getAppointments().add((AppointmentEntity) msg);
            }
            else  // isReserved=true and patient != null so we need to cancel the appointment
            {
                app.setReserved(false);
                app.setPatient(null);
                try {
                    client.sendToClient("reservation done!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            else {
//                try {
//                    client.sendToClient("failed to reserve the appointment!");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            session.beginTransaction();
            session.saveOrUpdate(app);
           // session.saveOrUpdate(((AppointmentEntity) msg).getPatient());
            session.flush();
            session.getTransaction().commit();
        }
        else if (msg.getClass().equals(UserEntity.class)){
            System.out.println(msg.toString());
            System.out.println(msg.getClass().toString());
            List<ManagerEntity> Mangers = getALLMangers();
           boolean flag_manager = checkPassword(Mangers,((UserEntity) msg),client);
            System.out.println("Flag Manager = " + flag_manager);
            List<DoctorEntity> Doctors = getALLDoctors();
            System.out.println("Size docs = "+ Doctors.size());
            boolean flag_doctor = checkPassword(Doctors,((UserEntity) msg),client);
            System.out.println("Flag Doctor = " + flag_doctor);
            List<NurseEntity> Nurses = getALLNurses();
            boolean flag_nurse = checkPassword(Nurses,((UserEntity) msg),client);
            System.out.println("Flag Nurse = " + flag_nurse);
            List<PatientEntity> Patients = getALLPatients();
            boolean flag_patient = checkPassword(Patients,((UserEntity) msg),client);
            System.out.println("Flag Patient = " + flag_patient);
            String stringResult="";
            if(flag_manager||flag_doctor||flag_patient||flag_nurse){
                System.out.println("flags ok");
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
                        e.printStackTrace();
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
        //session.getTransaction().begin();
        System.out.println("Update App");
        LocalDateTime now=LocalDateTime.now();
        now.getDayOfWeek();
        List<DoctorEntity> all_docs=getALLDoctors();
        for (DoctorEntity doc:all_docs) {
            List<DoctorClinicEntity> doc_clinics = doc.getDoctorClinicEntities();
            Set<AppointmentEntity> doc_appointments = doc.getAppointments();
            for (AppointmentEntity app:doc_appointments) {

                if(app.getDate().isBefore(now)) {
                    doc_appointments.remove(app);
                }
            }
            LocalDateTime latest_appointment;
            if(doc_appointments.size()>0)
            {
                //latest_appointment=doc_appointments.stream().toList().get(doc_appointments.size()-1).getDate();


                ArrayList<AppointmentEntity> allapps = new ArrayList<AppointmentEntity>();
                allapps.addAll(doc_appointments);
                latest_appointment=allapps.get(doc_appointments.size()-1).getDate();

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
                                    doc.getAppointments().add(app);

                                    session.getTransaction().begin();
                                    session.saveOrUpdate(app);
                                    session.flush();
                                    session.getTransaction().commit();

                                }
                            }
                        }
                    }
                    //session.flush();

                }
            }
        }
        //session.getTransaction().commit();
    }
    public static int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return (day.getValue());
    }

    private static void UpdateVaccineAppointments(){
        System.out.println("Update vaccine App");
        LocalDateTime now=LocalDateTime.now();
        now.getDayOfWeek();
        List<ClinicEntity> all_clinics =getALLClinics();
        for (ClinicEntity clinic:all_clinics) {
            List<VaccineAppointmentEntity> vaccine_appointments = clinic.getVac_appointments();
            for (VaccineAppointmentEntity app:vaccine_appointments) {
                if(app.getDate().isBefore(now)) {
                    vaccine_appointments.remove(app);
                }
            }
            LocalDateTime latest_appointment;
            if(vaccine_appointments.size()>0)
            {
                //latest_appointment=doc_appointments.stream().toList().get(doc_appointments.size()-1).getDate();
                ArrayList<VaccineAppointmentEntity> allvacapps = new ArrayList<VaccineAppointmentEntity>();
                allvacapps.addAll(vaccine_appointments);
                latest_appointment=allvacapps.get(vaccine_appointments.size()-1).getDate();
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
//                    for(DoctorClinicEntity doc_clinic : doc_clinics){
                    //    List<LocalTime> work_hours = clinic.GetWorkingDateTime();
                        LocalDate localDate=LocalDate.of(year,i,j);
                        int day_of_week= getDayNumberNew(localDate);
                        if(day_of_week==7){
                            day_of_week=1;
                        }else{
                            day_of_week++;
                        }
                        LocalTime opening = LocalTime.parse(clinic.getOpen());
                        LocalTime closing = LocalTime.parse(clinic.getClose());

                        if(!opening.toString().equals("00:00") && !closing.toString().equals("00:00") && day_of_week!= 6 && day_of_week != 7 && day_of_week != 2 && day_of_week != 3){
                            for(int hour=opening.getHour()*60;hour<=closing.getHour()*60;hour+=10) {
                                LocalDateTime appointment_time = LocalDateTime.of(year, i % 12, j, hour / 60, hour % 60);
                                if (!appointment_time.isBefore(now)) {

                                    VaccineAppointmentEntity app = new VaccineAppointmentEntity(appointment_time,10,clinic);
                                    clinic.getVac_appointments().add(app);
                                    session.getTransaction().begin();
                                    session.saveOrUpdate(app);
                                    session.flush();
                                    session.getTransaction().commit();

                                }
                            }
                        }
//                    }
                }
            }
        }
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
    public static List<AppointmentEntity> GetAllAppointments() {
        UpdateAppointments();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AppointmentEntity> query = builder.createQuery(AppointmentEntity.class);
        query.from(AppointmentEntity.class);
        List<AppointmentEntity> result = session.createQuery(query).getResultList();
        return result;
    }

    static <T> Predicate equal(CriteriaBuilder cb, Expression<T> left, T right) {
        return cb.equal(left, right);
    }

    private static AppointmentEntity get_app_with_id(int id)
    {
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AppointmentEntity> query = builder.createQuery(AppointmentEntity.class);
            Root<AppointmentEntity> tmp = query.from(AppointmentEntity.class);
            query.select(tmp);
            query.where(builder.equal(tmp.get("id"),id));
            TypedQuery<AppointmentEntity> q = session.createQuery(query);
            AppointmentEntity app = q.getSingleResult(); //getSingleResult();
            return app;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private static ArrayList<AppointmentEntity> get_apps_with_PatientId(int patient_id)
    {
        try {
            System.out.println("1");
            CriteriaBuilder builder = session.getCriteriaBuilder();
            System.out.println("2");
            CriteriaQuery<AppointmentEntity> query = builder.createQuery(AppointmentEntity.class);
            System.out.println("3");
            Root<AppointmentEntity> tmp = query.from(AppointmentEntity.class);
            System.out.println("4");
            query.select(tmp);
            System.out.println("5");
            query.where(builder.equal(tmp.get("patient"),patient_id));
            System.out.println("6");
            TypedQuery<AppointmentEntity> q = session.createQuery(query);
            System.out.println("7");
            ArrayList<AppointmentEntity> apps = (ArrayList<AppointmentEntity>) q.getResultList(); //getSingleResult();
            System.out.println("8");
            return apps;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static ManagerEntity get_manager_with_id(int id)
    {
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ManagerEntity> query = builder.createQuery(ManagerEntity.class);
            Root<ManagerEntity> tmp = query.from(ManagerEntity.class);
            query.select(tmp);
            query.where(builder.equal(tmp.get("manager_id"),id));
            TypedQuery<ManagerEntity> q = session.createQuery(query);
            ManagerEntity manager = q.getSingleResult(); //getSingleResult();
            return manager;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void executeSqlScript(File inputFile) {
        String delimiter = ";";
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }
        String rawStatement=scanner.toString();
        while(scanner.hasNext()) {
             rawStatement = scanner.next() + delimiter;
            System.out.println(rawStatement);
            try {
                session.getTransaction().begin();
                session.createSQLQuery(rawStatement).executeUpdate();
                session.flush();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        /*session.getTransaction().begin();
        session.createSQLQuery(rawStatement).executeUpdate();
        session.flush();
        session.getTransaction().commit();*/
        scanner.close();
    }
}
