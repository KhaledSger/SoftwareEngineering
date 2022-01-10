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
import java.util.ArrayList;
import java.util.List;

public class SimpleServer extends AbstractServer {
    private static Session session;
    private static List<ClinicEntity> Clinics;

    public void initSesssion() {
        session = getSessionFactory().openSession();
        try {
            String[] service = new String[]{"aaa", "bbb", "ccc"};
            ClinicEntity clinic1 = new ClinicEntity("Haifa clinic", "10:00", "20:00", service);
            session.save(clinic1);
            service = new String[]{"bbb", "ddd", "ccc"};
            ClinicEntity clinic2 = new ClinicEntity("Acre clinic", "12:00", "18:00", service);
            session.save(clinic2);
            service = new String[]{"aaa", "eee", "ddd"};
            ClinicEntity clinic3 = new ClinicEntity("Tel-Aviv clinic", "14:00", "22:00", service);
            session.save(clinic3);
            service = new String[]{"ww", "zz"};
            ClinicEntity clinic4 = new ClinicEntity("Eilaboun clinic", "08:00", "12:00", service);
            session.save(clinic4);
            PatientEntity pat1 = new PatientEntity(318588324,"Emad","Emad123",23,clinic1);
            session.save(pat1);
            PatientEntity pat2 = new PatientEntity(318234732,"Khaled","Khaled123",23,clinic4);
            session.save(pat2);
            NurseEntity nurse1 = new NurseEntity(792596666,"Good","Nurse","nursegood@gmail.com",clinic1);
            session.save(nurse1);
            DoctorEntity doc1= new DoctorEntity(2113423,"dr","fischer","drfischer@gmail.com","Neurology");
            session.save(doc1);
            AppointmentEntity app1= new AppointmentEntity("12:00","1/1/2021","12:00",clinic1,pat1,doc1);
            session.save(app1);
            ArrayList<String> times=new ArrayList<String>();
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("15:00-17:00");
            times.add("");
            times.add("");
            DoctorClinicEntity doctorClinic= new DoctorClinicEntity(doc1,clinic3,times);
            session.save(doctorClinic);
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
                List<ClinicEntity> clnics = getALLClinics();
                Clinics = clnics;
                client.sendToClient(clnics);
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
    private static List<DoctorClinicEntity> getALLDoctorClinics() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DoctorClinicEntity> query = builder.createQuery(DoctorClinicEntity.class);
        query.from(DoctorClinicEntity.class);
        List<DoctorClinicEntity> result = session.createQuery(query).getResultList();
        return result;
    }
}
