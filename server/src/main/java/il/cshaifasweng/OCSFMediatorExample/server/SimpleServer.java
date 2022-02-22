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
            PatientEntity pat1 = new PatientEntity(318588324,"Emad","daraw","Emad123@gmail.com","Em12345",17,clinic3);
            session.save(pat1);
            PatientEntity pat2 = new PatientEntity(318234732,"Khaled","Sger","Khaled123@gmail.com","Kh12345",23,clinic4);
            session.save(pat2);
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
        System.out.println("msg: "+msgString);
        System.out.println(msg.getClass().toString());
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
            System.out.println("patient-size= " + Patients.size());
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
        }
        else if(msgString.startsWith("#updateAppsForDoc:"))
        {
            msgString = msgString.substring(18);
            System.out.println("msgString= "+msgString);
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
                System.out.println("entering the else cancel");
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
        System.out.println("all docs size: "+all_docs.size());
        for (DoctorEntity doc:all_docs) {
            List<DoctorClinicEntity> doc_clinics = doc.getDoctorClinicEntities();
            System.out.println("doc clinic size: "+doc_clinics.size());
            List<AppointmentEntity> doc_appointments = doc.getAppointments();
            for (AppointmentEntity app:doc_appointments) {

                if(app.getDate().isBefore(now)) {
                    doc_old_apps.add(app); //adding the appointment to the old apps array
                    Appointments.remove(app); //removing the appointment from the array of the current apps
                }
            }
            LocalDateTime latest_appointment;
            if(doc_appointments.size()>0)
            {
                //latest_appointment=doc_appointments.stream().toList().get(doc_appointments.size()-1).getDate();

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

    private static void UpdateVaccineAppointments(){
        System.out.println("Update vaccine App");
        LocalDateTime now=LocalDateTime.now();
        now.getDayOfWeek();
        List<ClinicEntity> all_clinics =getALLClinics();
        for (ClinicEntity clinic:all_clinics) {
            List<VaccineAppointmentEntity> vaccine_appointments = clinic.getVac_appointments();
            for (VaccineAppointmentEntity app:vaccine_appointments) {
                if(app.getDate().isBefore(now)) {
                    vaccine_old_apps.add(app);
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
            for (int i = latest_appointment.getMonthValue() ; i <= (now.getMonthValue()+1); i++)
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

                                    VaccineAppointmentEntity covid_app = new VaccineAppointmentEntity(appointment_time,10,clinic,"covid");
                                    VaccineAppointmentEntity flu_app = new VaccineAppointmentEntity(appointment_time,10,clinic,"flu");
//                                    clinic.getVac_appointments().add(covid_app);
//                                    clinic.getVac_appointments().add(flu_app);
                                    session.getTransaction().begin();
                                    session.saveOrUpdate(covid_app);
                                    session.saveOrUpdate(flu_app);
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

    public static void UpdateReports()
    {
        int[] doc_reports = new int[Clinics.size()*2];
        for(AppointmentEntity app : doc_old_apps)
        {
            if (app.getDate().toLocalDate().equals(LocalDate.now()))
            {
              if (app.getDoctor().getSpecialization().equals("Family Doctor"))
              {
                  if(!app.getActual_date().equals(null))
                  {
                      doc_reports[app.getClinic().getId() * 2 - 2] += 1;
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
                  }
                  else {
                      app.getClinic().getReports2()[1]+=1;
                  }
              }
           }
        }
        int[] vac_reports = new int[Clinics.size()];
        for(VaccineAppointmentEntity app : vaccine_old_apps)
        {
            if (app.getDate().toLocalDate().equals(LocalDate.now()))
            {
                if (!app.getActual_date().equals(null))
                {
                    vac_reports[app.getClinic().getId()-1]+=1;
                }
                else{
                    app.getClinic().getReports2()[2]+=1;
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
        }
        //TODO add covid test reports
    }

    public static void UpdateWeeklyReports()
    {
       // session.getTransaction().begin();
        String[] arr = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        for(ClinicEntity clinic : Clinics)
        {
            String report="";
            for(int i=0;i<7;i++)
            {
                report += arr[i] + ":\n" + "Family Doctor: " + clinic.getReports()[i][0] + ", Children Doctor: " + clinic.getReports()[i][1]
                        + ", vaccine treatment: " + clinic.getReports()[i][2] +", covid test: " + clinic.getReports()[i][3] + ", lab test: "
                        + clinic.getReports()[i][4] +", nurse appointment: " + clinic.getReports()[i][5] +"\n";
            }

            for (int i = 0; i < 4 ;i++){

                clinic.getReports2()[i] = 0;
            }
            session.beginTransaction();
            clinic.setReport1(report);
            session.saveOrUpdate(clinic);
            session.flush();
            session.getTransaction().commit();
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
       // UpdateAppointments();
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
}
