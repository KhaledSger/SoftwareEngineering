package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;



public class SimpleServer extends AbstractServer {
    public static Session session;
    private static List<ClinicEntity> Clinics;
    private static List<PatientEntity> Patients ;
    private static List<ManagerEntity> managers;
    public static ArrayList<AppointmentEntity> Appointments=new ArrayList<AppointmentEntity>();
    public static ArrayList<VaccineAppointmentEntity> vaccine_Appointments=new ArrayList<VaccineAppointmentEntity>();
    private static ArrayList<AppointmentEntity> doc_old_apps = new ArrayList<AppointmentEntity>();
    private static ArrayList<VaccineAppointmentEntity> vaccine_old_apps = new ArrayList<VaccineAppointmentEntity>();


    public void initSesssion() {
        session = getSessionFactory().openSession();
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

//            ClinicEntity clinic1 = new ClinicEntity("Haifa clinic", "10:00", "20:00", service, new ArrayList<PatientEntity>());
//            clinic1.setReport1("Sunday: Family Doctor: 11,Children Doctor: 12, vaccine treatment: 20, covid test: 89, lab test: 5, nurse appointment:17" + "\n" +
//                    " Monday: \nFamily Doctor: 15,Children Doctor:19 , vaccine treatment: 20, covid test: 50, lab test: 10, nurse appointment: 16" + "\n" +
//                    " Tuesday: \nFamily Doctor: 4,Children Doctor: 8, vaccine treatment: 32, covid test: 14, lab test: 17, nurse appointment: 3" + "\n" +
//                    " Wednesday:\n Family Doctor: 5,Children Doctor: 13, vaccine treatment: 17, covid test: 14, lab test: 4, nurse appointment: 4" + "\n" +
//                    " Thursday: \nFamily Doctor: 7,Children Doctor: 9, vaccine treatment: 12, covid test: 11, lab test: 10, nurse appointment: 8" + "\n" +
//                    " Friday: \nFamily Doctor: 19,Children Doctor: 20, vaccine treatment: 21, covid test: 22, lab test: 16, nurse appointment: 15" + "\n" +
//                    " Saturday: \nFamily Doctor: 21,Children Doctor: 14, vaccine treatment: 4, covid test: 8, lab test: 9, nurse appointment: 10"
//            );
//            clinic1.setReport2("Family Doctor: 3 \nChildren Doctor: 4 \nVaccine treatment: 2 \nCovid Test: 5");
//            clinic1.setReport3("Sunday: 1 \nMonday: 2 \nTuesday: 2 \nWednesday: 3 \nThursday: 1 \nFriday: 0 \nSaturday:1");
//            session.save(clinic1);
//
//            service = new String[]{"bbb", "ddd", "ccc"};
//            ClinicEntity clinic2 = new ClinicEntity("Acre clinic", "12:00", "18:00", service, new ArrayList<PatientEntity>());
//            clinic2.setReport1("Sunday: Family Doctor:18 ,Children Doctor: 2, vaccine treatment: 19, covid test: 11, lab test: 3, nurse appointment: 11" + "\n" +
//                    " Monday: \n Family Doctor: 5,Children Doctor:9 , vaccine treatment: 23, covid test: 36, lab test: 2, nurse appointment: 3" + "\n" +
//                    " Tuesday: \nFamily Doctor: 13,Children Doctor: 1, vaccine treatment: 21, covid test: 12, lab test: 5, nurse appointment: 12" + "\n" +
//                    " Wednesday: \nFamily Doctor: 2,Children Doctor: 3, vaccine treatment: 17, covid test: 15, lab test: 7, nurse appointment: 21" + "\n" +
//                    " Thursday: \nFamily Doctor: 8,Children Doctor: 6, vaccine treatment: 15, covid test: 19, lab test: 3, nurse appointment: 12" + "\n" +
//                    " Friday: \nFamily Doctor: 19,Children Doctor: 5, vaccine treatment: 13, covid test: 23, lab test: 2, nurse appointment: 19" + "\n" +
//                    " Saturday: \nFamily Doctor: 20,Children Doctor: 9, vaccine treatment: 14, covid test: 13, lab test: 8, nurse appointment: 33"
//            );
//            clinic2.setReport2("Family Doctor: 4 \nChildren Doctor: 0 \nVaccine treatment: 1 \nCovid Test: 3");
//            clinic2.setReport3("Sunday: 3 \nMonday: 4 \nTuesday: 1 \nWednesday: 0 \nThursday: 2 \nFriday: 3 \nSaturday:1");
//            session.save(clinic2);
//
//            service = new String[]{"aaa", "eee", "ddd"};
//            ClinicEntity clinic3 = new ClinicEntity("Tel-Aviv clinic", "14:00", "22:00", service, new ArrayList<PatientEntity>());
//            clinic3.setReport1("Sunday: Family Doctor:17 ,Children Doctor: 3, vaccine treatment: 20, covid test: 12, lab test: 11, nurse appointment: 15" + "\n" +
//                    " Monday: \nFamily Doctor: 15,Children Doctor:19 , vaccine treatment: 20, covid test: 50, lab test: 10, nurse appointment: 16" + "\n" +
//                    " Tuesday: \nFamily Doctor: 2,Children Doctor: 23, vaccine treatment: 18, covid test: 19, lab test: 14, nurse appointment: 17" + "\n" +
//                    " Wednesday: \nFamily Doctor: 8,Children Doctor: 10, vaccine treatment: 31, covid test: 34, lab test: 21, nurse appointment: 13" + "\n" +
//                    " Thursday: \nFamily Doctor: 2,Children Doctor: 3, vaccine treatment: 6, covid test: 32, lab test: 3, nurse appointment: 18" + "\n" +
//                    " Friday: \nFamily Doctor: 6,Children Doctor: 7, vaccine treatment: 9, covid test: 3, lab test: 19, nurse appointment: 15" + "\n" +
//                    " Saturday: \nFamily Doctor: 6,Children Doctor: 6, vaccine treatment: 10, covid test: 14, lab test: 9, nurse appointment: 5"
//            );
//            clinic3.setReport2("Family Doctor: 5 \nChildren Doctor: 2 \nVaccine treatment: 1 \nCovid Test: 9");
//            clinic3.setReport3("Sunday: 0 \nMonday: 10 \nTuesday: 4 \nWednesday: 6 \nThursday: 2 \nFriday: 3 \nSaturday:1");
//            session.save(clinic3);
//
//            service = new String[]{"ww", "zz"};
//            ClinicEntity clinic4 = new ClinicEntity("Eilaboun clinic", "08:00", "12:00", service, new ArrayList<PatientEntity>());
//            clinic4.setReport1("Sunday: Family Doctor: 13,Children Doctor: 23, vaccine treatment: 22, covid test: 31, lab test: 8, nurse appointment: 9" + "\n" +
//                    " Monday: \nFamily Doctor: 13,Children Doctor:9 , vaccine treatment: 10, covid test: 15, lab test: 1, nurse appointment: 6" + "\n" +
//                    " Tuesday: \nFamily Doctor: 23,Children Doctor: 26, vaccine treatment: 33, covid test: 22, lab test: 11, nurse appointment: 14" + "\n" +
//                    " Wednesday: \nFamily Doctor: 16,Children Doctor: 7, vaccine treatment: 7, covid test: 3, lab test: 7, nurse appointment: 11" + "\n" +
//                    " Thursday: \nFamily Doctor: 6,Children Doctor: 5, vaccine treatment: 31, covid test: 10, lab test: 13, nurse appointment: 29" + "\n" +
//                    " Friday: \nFamily Doctor: 13,Children Doctor: 14, vaccine treatment: 15, covid test: 12, lab test: 9, nurse appointment: 3" + "\n" +
//                    " Saturday: \nFamily Doctor: 3,Children Doctor: 8, vaccine treatment: 9, covid test: 2, lab test: 1, nurse appointment: 8"
//            );
//            clinic4.setReport2("Family Doctor: 1 \nChildren Doctor: 1 \nVaccine treatment: 1 \nCovid Test: 1");
//            clinic4.setReport3("Sunday: 1 \nMonday: 3 \nTuesday: 2 \nWednesday: 4 \nThursday: 3 \nFriday: 6 \nSaturday: 1");
//            session.save(clinic4);

            PatientEntity pat1 = new PatientEntity(318588324,"Emad","daraw","Emad123@gmail.com","Em12345",17,clinic3);
            pat1.setgPassExp(LocalDate.now().plusMonths(5).toString());
            session.save(pat1);
            PatientEntity pat2 = new PatientEntity(318234732,"Khaled","Sger","Khaled123@gmail.com","Kh12345",23,clinic4);
            session.save(pat2);
            PatientEntity pat3 = new PatientEntity(123456789,"Basel","Mousa","mousabasel@gmail.com","B12345",23,clinic4);
            session.save(pat3);
            PatientEntity pat4 = new PatientEntity(314325457,"Malki","Grosman","malkigr@gmail.com","Kh12345",23,clinic3);
            session.save(pat4);
            NurseEntity nurse1 = new NurseEntity(792596666,"Good","Nurse","nursegood@gmail.com","Goo123",clinic1);
            session.save(nurse1);
            DoctorEntity doc1= new DoctorEntity(2113423,"dr","fischer","drfischer@gmail.com","111","Neurology");
            session.save(doc1);
            DoctorEntity doc2= new DoctorEntity(2113456,"dr","family","drfamily@gmail.com","11111","Family Doctor");
            session.save(doc2);
            DoctorEntity doc3= new DoctorEntity(2116979,"dr","children","drchildren@gmail.com","1234","Children Doctor");
            session.save(doc3);
            DoctorEntity doc4= new DoctorEntity(2123124,"dr","emad","dremad@gmail.com","1234567","Neurology");
            session.save(doc4);
            DoctorEntity doc5= new DoctorEntity(3142315,"dr","khaled","drkhaled@gmail.com","123","Neurology");
            session.save(doc5);
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
            DoctorClinicEntity doctorClinic1= new DoctorClinicEntity(doc2,clinic3,times);
            session.save(doctorClinic1);
            DoctorClinicEntity doctorClinic2= new DoctorClinicEntity(doc3,clinic3,times);
            session.save(doctorClinic2);
            DoctorClinicEntity doctorClinic3= new DoctorClinicEntity(doc4,clinic2,times);
            session.save(doctorClinic3);
            DoctorClinicEntity doctorClinic4= new DoctorClinicEntity(doc5,clinic1,times);
            session.save(doctorClinic4);
            ManagerEntity manger = new ManagerEntity(doc1.getId(), doc1.getFirst_name(), doc1.getFamily_name(),
                    doc1.getMail(),"111",clinic3);
            session.save(manger);
             ManagerEntity manger1 = new ManagerEntity(doc2.getId(), doc2.getFirst_name(), doc2.getFamily_name(),
                     doc2.getMail(),"111",clinic1);
              session.save(manger1);
               ManagerEntity manger2 = new ManagerEntity(doc3.getId(), doc3.getFirst_name(), doc3.getFamily_name(),
                       doc3.getMail(),"111",clinic2);
            session.save(manger2);
             ManagerEntity manger3 = new ManagerEntity(doc4.getId(), doc4.getFirst_name(), doc4.getFamily_name(),doc4.getMail(),"111",clinic4);
             session.save(manger3);
              DoctorPatientEntity docpat = new DoctorPatientEntity(doc1,pat1);
              session.save(docpat);
            session.flush();
            session.getTransaction().commit();
            UpdateAppointments();
            Appointments = (ArrayList<AppointmentEntity>) GetAllAppointments();
            UpdateVaccineAppointments();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        }

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
                ArrayList<VaccineAppointmentEntity> patient_vac_apps=get_vac_apps_with_PatientId((Integer.parseInt(msgString)));
                ArrayList<AppointmentEntity> patient_apps=get_apps_with_PatientId((Integer.parseInt(msgString)));
                client.sendToClient(patient_apps);
                client.sendToClient("patient vac:"+patient_vac_apps);
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
                client.sendToClient(clinics);
                System.out.format("Sent all clinics to client %s\n", client.getInetAddress().getHostAddress());
            } catch (Exception e) {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            }
        }
        else if(msgString.startsWith("#updateAppsForDoc:"))
        {
            msgString = msgString.substring(18);
            int i =Integer.parseInt(msgString);
            AppointmentEntity current_app = Appointments.get(i);
            while(Appointments.get(i).getDoctor().getDoctor_id()==current_app.getDoctor().getDoctor_id() && Appointments.get(i).getClinic().getId()==current_app.getClinic().getId()
            && ((Appointments.get(i).getDate().equals(Appointments.get(i-1).getDate().plusMinutes(20))) || (Appointments.get(i).getDate().equals(Appointments.get(i-1).getDate().plusMinutes(15)))) )
            {
                AppointmentEntity tmp = Appointments.get(i);
                LocalDateTime time = tmp.getDate();
                if(tmp.getActual_date() != null)
                {
                    time = tmp.getActual_date();
                }
                if(Appointments.get(i).getDoctor().getSpecialization().equals("Family Doctor") || Appointments.get(i).getDoctor().getSpecialization().equals("children Doctor") )
                {
                    tmp.setActual_date(time.plusMinutes(15));
                }
                else {
                    tmp.setActual_date(time.plusMinutes(20));
                }
                if(Appointments.get(i).getPatient().equals(null)) //if we found an appointment with a null patient this means that we have already updated all the reserved appointments
                {
                    break;
                }

            }
        }
        else if(msgString.startsWith("#increase nurse app:"))
        {
            LocalDate localDate=LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
            int day_of_week= getDayNumberNew(localDate);
            if(day_of_week==7)
                day_of_week=0;
            msgString = msgString.substring(20);
            for(ClinicEntity clinic : Clinics)
            {
                if(clinic.getId() == Integer.parseInt(msgString))
                {
                    clinic.getReports()[day_of_week][5]+=1;
                    break;
                }
            }
        }
        else if(msgString.startsWith("#increase lab app:"))
        {
            LocalDate localDate=LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
            int day_of_week= getDayNumberNew(localDate);
            if(day_of_week==7)
                day_of_week=0;
            msgString = msgString.substring(18);
            for(ClinicEntity clinic : Clinics)
            {
                if(clinic.getId() == Integer.parseInt(msgString))
                {
                    clinic.getReports()[day_of_week][4]+=1;
                }
            }
        }
        else if (msg.getClass().equals(ClinicEntity.class)) {
            for (int i = 0; i < Clinics.size(); i++) {
                if (Clinics.get(i).getId() == ((ClinicEntity) msg).getId()) {
                    session.beginTransaction();
                    Clinics.get(i).setOpen(((ClinicEntity) msg).getOpen());
                    Clinics.get(i).setClose(((ClinicEntity) msg).getClose());
                    Clinics.get(i).setVac_test_open(((ClinicEntity) msg).getVac_test_open());
                    Clinics.get(i).setVac_test_close(((ClinicEntity) msg).getVac_test_close());
                    session.save(Clinics.get(i));
                    session.flush();
                    session.getTransaction().commit();
                    System.out.format("Updating all clinics on client %s\n", client.getInetAddress().getHostAddress());
                }
            }
        }
        else if (msg.getClass().equals(AppointmentEntity.class))
        {
            AppointmentEntity app=get_app_with_id(((AppointmentEntity) msg).getId());
            System.out.println(app.getDate());
            if(!((AppointmentEntity) msg).isReserved()) // the client has pressed on app but not confirmed the reservation yet
            {
                app.setReserved(true);

            }
            else if((app.getPatient()==null) ) { // the client has confirmed the reservation
                app.setPatient(((AppointmentEntity) msg).getPatient());
                EmailUtil.sendEmail((((AppointmentEntity) msg).getPatient()).getMail(),"appointment confirmation","you have appointment in :"+app.getClinic().getName().toString()+"\n"+"with doctor: "+app.getDoctor().getFamily_name().toString()+"\n"+"at : "+app.getDate());
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
                    client.sendToClient("Appointment Cancelled!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            session.beginTransaction();
            session.saveOrUpdate(app);
           // session.saveOrUpdate(((AppointmentEntity) msg).getPatient());
            session.flush();
            session.getTransaction().commit();
        }
        else if (msg.getClass().equals(VaccineAppointmentEntity.class))
        {
            System.out.println("msg id "+((VaccineAppointmentEntity) msg).getId());
            VaccineAppointmentEntity app=get_vac_app_with_id(((VaccineAppointmentEntity) msg).getId());
            System.out.println(app.getDate());
            if(!((VaccineAppointmentEntity) msg).isReserved()) // the client has pressed on app but not confirmed the reservation yet
            {
                app.setReserved(true);

            }
            else if((app.getPatient()==null) ) { // the client has confirmed the reservation
                app.setPatient(((VaccineAppointmentEntity) msg).getPatient());
                EmailUtil.sendEmail((((VaccineAppointmentEntity) msg).getPatient()).getMail(),"vaccine appointment confirmation","you have a  vaccine appointment in :"+app.getClinic().getName().toString()+"\n"+"at : "+app.getDate());
                try {
                    client.sendToClient("reservation done!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                app.setPatient(((VaccineAppointmentEntity) msg).getPatient());
                ((VaccineAppointmentEntity) msg).getPatient().getVac_appointments().add((VaccineAppointmentEntity) msg);
            }
            else  // isReserved=true and patient != null so we need to cancel the appointment
            {
                app.setReserved(false);
                app.setPatient(null);
                try {
                    client.sendToClient("Appointment Cancelled!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            session.beginTransaction();
            session.saveOrUpdate(app);
            session.flush();
            session.getTransaction().commit();
        }
        else if (msg.getClass().equals(UserEntity.class)){
            System.out.println(msg.toString());
            System.out.println(msg.getClass().toString());
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
    <T extends UserEntity> boolean checkPassword(List<T> Users,UserEntity user,ConnectionToClient client){  // check if the entered username and password exists and correct
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


    private static void UpdateAppointments(){  // update the appointments for the doctors by their working hours
        //session.getTransaction().begin();
        System.out.println("Update App");
        LocalDateTime now=LocalDateTime.now();
        now.getDayOfWeek();
        List<DoctorEntity> all_docs=getALLDoctors();
        for (DoctorEntity doc:all_docs) {
            List<DoctorClinicEntity> doc_clinics = doc.getDoctorClinicEntities();
            List<AppointmentEntity> doc_appointments = doc.getAppointments();
            for (AppointmentEntity app:doc_appointments) {

                if(app.getDate().isBefore(now)) {
                    if(app.isReserved()) {
                        doc_old_apps.add(app);
                    }//adding the appointment to the old apps array
                    Appointments.remove(app); //removing the appointment from the array of the current apps
                }
            }
            LocalDateTime latest_appointment;
            if(doc_appointments.size()>0)
            {
                doc_appointments.sort(Comparator.comparing(o -> o.getDate()));
                latest_appointment=doc_appointments.get(doc_appointments.size()-1).getDate();
                System.out.println(latest_appointment.toString());

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
                        int duration=20;
                        if(doc_clinic.getDoctor().getSpecialization().equals("Family Doctor") ||doc_clinic.getDoctor().getSpecialization().equals("Children Doctor") )
                        {
                            duration=15;
                        }
                        if(!opening.toString().equals("00:00") && !closing.toString().equals("00:00")){
                            for(int hour=opening.getHour()*60;hour<=closing.getHour()*60;hour+=duration) {
                                LocalDateTime appointment_time = LocalDateTime.of(year, i % 12, j, hour / 60, hour % 60);
                                if (!appointment_time.isBefore(now)) {

                                    AppointmentEntity app = new AppointmentEntity(appointment_time, doc_clinic, duration);
                                   // doc.getAppointments().add(app);

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

    private static void UpdateVaccineAppointments(){   // update vaccine and covid test appointments by working hours
        LocalDateTime now=LocalDateTime.now();
        now.getDayOfWeek();
        List<ClinicEntity> all_clinics =getALLClinics();
        for (ClinicEntity clinic:all_clinics) {
            List<VaccineAppointmentEntity> vaccine_appointments = clinic.getVac_appointments();
            for (VaccineAppointmentEntity app:vaccine_appointments) {
                if(app.getDate().isBefore(now)) {
                    if(app.isReserved()) {
                        vaccine_old_apps.add(app);
                        if(app.getType().equals("covid")){
                            PatientEntity patient = app.getPatient();
                            patient.setgPassExp(app.getDate().plusMonths(6).toString());
                            session.getTransaction().begin();
                            session.saveOrUpdate(patient);
                            session.flush();
                            session.getTransaction().commit();

                        }
                    }
                    vaccine_appointments.remove(app);
                }
            }
            LocalDateTime latest_appointment;
            if(vaccine_appointments.size()>0)
            {
                //latest_appointment=doc_appointments.stream().toList().get(doc_appointments.size()-1).getDate();
              /*  ArrayList<VaccineAppointmentEntity> allvacapps = new ArrayList<VaccineAppointmentEntity>();
                allvacapps.addAll(vaccine_appointments);*/
                vaccine_appointments.sort(Comparator.comparing(o -> o.getDate()));
                latest_appointment=vaccine_appointments.get(vaccine_appointments.size()-1).getDate();
            }else{
                latest_appointment = now;
            }
            for (int i = latest_appointment.getMonthValue() ; i <= (now.getMonthValue()+1); i++)
            {
                int year= now.getYear();
                if(i>12){
                    year++;
                }
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
                        LocalTime opening = LocalTime.parse(clinic.getVac_test_open());
                        LocalTime closing = LocalTime.parse(clinic.getVac_test_close());

                        if(day_of_week!= 6 && day_of_week != 7 && day_of_week != 2 && day_of_week != 3){
                            for(int hour=opening.getHour()*60;hour<=closing.getHour()*60;hour+=10) {
                                LocalDateTime appointment_time = LocalDateTime.of(year, i % 12, j, hour / 60, hour % 60);
                                if (!appointment_time.isBefore(now)) {
                                    VaccineAppointmentEntity covid_app = new VaccineAppointmentEntity(appointment_time,10,clinic,"covid");
                                    VaccineAppointmentEntity flu_app = new VaccineAppointmentEntity(appointment_time,10,clinic,"flu");
                                    VaccineAppointmentEntity cov_test = new VaccineAppointmentEntity(appointment_time,10,clinic,"covid test");
//                                    clinic.getVac_appointments().add(covid_app);
//                                    clinic.getVac_appointments().add(flu_app);
                                    session.getTransaction().begin();
                                    session.saveOrUpdate(covid_app);
                                    session.saveOrUpdate(flu_app);
                                    session.saveOrUpdate(cov_test);
                                    session.flush();
                                    session.getTransaction().commit();

                                }
                            }
                        }
//                    }
                }
            }
        }
        System.out.println("Vaccine Update Done");
    }

    public static void UpdateReports()
    {
        int[] doc_reports = new int[Clinics.size()*2];
        int[] avg_wait_report = new int[Clinics.size()*2];
        for(AppointmentEntity app : doc_old_apps)
        {
            if (app.getDate().toLocalDate().equals(LocalDate.now()))
            {
              if (app.getDoctor().getSpecialization().equals("Family Doctor"))
              {
                  if(!app.getActual_date().equals(null))
                  {
                      doc_reports[app.getClinic().getId() * 2 - 2] += 1;
                      Duration d = Duration.between(app.getDate(),app.getActual_date());
                      avg_wait_report[app.getClinic().getId()*2-2]+= d.toMinutes();
                      avg_wait_report[app.getClinic().getId()*2-1]+=1;
                  }
                  else {
                      app.getClinic().getReports2()[0]+=1;
                  }
              }
              else if (app.getDoctor().getSpecialization().equals("Children Doctor"))
              {
                  if(!app.getActual_date().equals(null))
                  {
                      doc_reports[app.getClinic().getId() * 2 - 1 ] += 1;
                      Duration d = Duration.between(app.getDate(),app.getActual_date());
                      avg_wait_report[app.getClinic().getId()*2-2]+= d.toMinutes();
                      avg_wait_report[app.getClinic().getId()*2-1]+=1;
                  }
                  else {
                      app.getClinic().getReports2()[1]+=1;
                  }
              }
           }
        }
        int[] vac_reports = new int[Clinics.size()];
        int[] vac_test= new int[Clinics.size()];
        for(VaccineAppointmentEntity app : vaccine_old_apps)
        {
            if (app.getDate().toLocalDate().equals(LocalDate.now()))
            {
                if(app.getType().equals("covid test")){
                    if (!app.getActual_date().equals(null)){
                        vac_test[app.getClinic().getId()-1]+=1;
                    }else{
                        app.getClinic().getReports2()[3]+=1;
                    }


                }else{
                    if (!app.getActual_date().equals(null))
                    {
                        vac_reports[app.getClinic().getId()-1]+=1;
                    }
                    else{
                        app.getClinic().getReports2()[2]+=1;
                    }

                }


            }
        }
        LocalDate localDate=LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
        int day_of_week= getDayNumberNew(localDate);
        if(day_of_week==7)
            day_of_week=0;
        for(ClinicEntity clinic : Clinics)
        {
            clinic.getReports()[day_of_week][0] = doc_reports[clinic.getId()*2-2]; // family doctor
            clinic.getReports()[day_of_week][1] = doc_reports[clinic.getId()*2-1]; // children doctor
            clinic.getReports()[day_of_week][2] = vac_reports[clinic.getId()-1]; // vaccine reports
            clinic.getReports()[day_of_week][3] = vac_test[clinic.getId()-1];
            if(avg_wait_report[clinic.getId()*2-1]>0){
                clinic.getReports3()[day_of_week]=avg_wait_report[clinic.getId()*2-2]/avg_wait_report[clinic.getId()*2-1];
            }
        }

    }

    public static void UpdateWeeklyReports()  // update the weekly report
    {
       // session.getTransaction().begin();
        String[] arr = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        for(ClinicEntity clinic : Clinics)
        {
            String report="";
            String report3 ="";
            for(int i=0;i<7;i++)
            {
                report += arr[i] + ":\n" + "Family Doctor: " + clinic.getReports()[i][0] + ", Children Doctor: " + clinic.getReports()[i][1]
                        + ", vaccine treatment: " + clinic.getReports()[i][2] +", covid test: " + clinic.getReports()[i][3] + ", lab test: "
                        + clinic.getReports()[i][4] +", nurse appointment: " + clinic.getReports()[i][5] +"\n";
                for(int j=0; j<6;j++){
                    clinic.getReports()[i][j]=0;
                }
                report3 += arr[i] +": "+ clinic.getReports3()[i]+"\n";
                clinic.getReports3()[i]=0;
            }
             String report2 ="";
            report2+= "Family Doctor: "+clinic.getReports2()[0] + "\nChildren Doctor: " + clinic.getReports2()[1]
                      +"\nVaccine treatment: " + clinic.getReports2()[2] + "\nCovid Test: " + clinic.getReports2()[3];

            for (int i = 0; i < 4 ;i++){

                clinic.getReports2()[i] = 0;
            }
            session.beginTransaction();
            clinic.setReport2(report2);
            clinic.setReport1(report);
            clinic.setReport3(report3);
            session.saveOrUpdate(clinic);
            session.flush();
            session.getTransaction().commit();
        }
    }

    private static List<ClinicEntity> getALLClinics() { // get all the clinics from the database3
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClinicEntity> query = builder.createQuery(ClinicEntity.class);
        query.from(ClinicEntity.class);
        List<ClinicEntity> result = session.createQuery(query).getResultList();
        return result;
    }

    private static List<PatientEntity> getALLPatients()  // get all the patients from the database
    {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PatientEntity> query = builder.createQuery(PatientEntity.class);
        query.from(PatientEntity.class);
        List<PatientEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<NurseEntity> getALLNurses() {  // get all the nurses from the database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<NurseEntity> query = builder.createQuery(NurseEntity.class);
        query.from(NurseEntity.class);
        List<NurseEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<DoctorEntity> getALLDoctors() {  // get all the doctors from the database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DoctorEntity> query = builder.createQuery(DoctorEntity.class);
        query.from(DoctorEntity.class);
        List<DoctorEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    private static List<ManagerEntity> getALLMangers() { // get all the managers from the database
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
    public static List<AppointmentEntity> GetAllAppointments() {  // get all the appointments from the database
       // UpdateAppointments();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AppointmentEntity> query = builder.createQuery(AppointmentEntity.class);
        query.from(AppointmentEntity.class);
        List<AppointmentEntity> result = session.createQuery(query).getResultList();
        return result;
    }
    public static List<VaccineAppointmentEntity> GetAllVacAppointments() {  // get all the vaccine appointments from the database
        // UpdateAppointments();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<VaccineAppointmentEntity> query = builder.createQuery(VaccineAppointmentEntity.class);
        query.from(VaccineAppointmentEntity.class);
        List<VaccineAppointmentEntity> result = session.createQuery(query).getResultList();
        return result;
    }

    static <T> Predicate equal(CriteriaBuilder cb, Expression<T> left, T right) {
        return cb.equal(left, right);
    }

    private static AppointmentEntity get_app_with_id(int id)  // get the appointment with the same id from the database
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

    private static VaccineAppointmentEntity get_vac_app_with_id(int id)  // get the vaccine appointment with the same id from the database
    {
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<VaccineAppointmentEntity> query = builder.createQuery(VaccineAppointmentEntity.class);
            Root<VaccineAppointmentEntity> tmp = query.from(VaccineAppointmentEntity.class);
            query.select(tmp);
            query.where(builder.equal(tmp.get("id"),id));
            TypedQuery<VaccineAppointmentEntity> q = session.createQuery(query);
            VaccineAppointmentEntity app = q.getSingleResult(); //getSingleResult();
            return app;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private static ArrayList<AppointmentEntity> get_apps_with_PatientId(int patient_id)  // get all the appointments of the given patient
    {
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AppointmentEntity> query = builder.createQuery(AppointmentEntity.class);
            Root<AppointmentEntity> tmp = query.from(AppointmentEntity.class);
            query.select(tmp);
            query.where(builder.equal(tmp.get("patient"),patient_id));
            TypedQuery<AppointmentEntity> q = session.createQuery(query);
            ArrayList<AppointmentEntity> apps = (ArrayList<AppointmentEntity>) q.getResultList(); //getSingleResult();
            return apps;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<VaccineAppointmentEntity> get_vac_apps_with_PatientId(int patient_id)  // get all the vaccine appointments of the given patient
    {
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<VaccineAppointmentEntity> query = builder.createQuery(VaccineAppointmentEntity.class);
            Root<VaccineAppointmentEntity> tmp = query.from(VaccineAppointmentEntity.class);
            query.select(tmp);
            query.where(builder.equal(tmp.get("patient"),patient_id));
            TypedQuery<VaccineAppointmentEntity> q = session.createQuery(query);
            ArrayList<VaccineAppointmentEntity> apps = (ArrayList<VaccineAppointmentEntity>) q.getResultList(); //getSingleResult();
            return apps;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static ManagerEntity get_manager_with_id(int id)  // get the manager with the given id from the database
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
}
