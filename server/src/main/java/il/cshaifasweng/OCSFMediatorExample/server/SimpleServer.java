package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ClincEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
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
import java.util.List;

public class SimpleServer extends AbstractServer {
    private static Session session;
    private static List<ClincEntity> Clinics;

    public void initSesssion() {
        session = getSessionFactory().openSession();
        try {
            String[] service = new String[]{"aaa", "bbb", "ccc"};
            ClincEntity clinc1 = new ClincEntity("Haifa clinic", "10:00", "20:00", service);
            session.save(clinc1);
            service = new String[]{"bbb", "ddd", "ccc"};
            ClincEntity clinc2 = new ClincEntity("Acre clinic", "12:00", "18:00", service);
            session.save(clinc2);
            service = new String[]{"aaa", "eee", "ddd"};
            ClincEntity clinc3 = new ClincEntity("Tel-Aviv clinic", "14:00", "22:00", service);
            session.save(clinc3);
            service = new String[]{"ww", "zz"};
            ClincEntity clinc4 = new ClincEntity("Eilaboun clinic", "08:00", "12:00", service);
            session.save(clinc4);
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
        configuration.addAnnotatedClass(ClincEntity.class);
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
                List<ClincEntity> clnics = getALLClinics();
                Clinics = clnics;
                client.sendToClient(clnics);
                System.out.format("Sent all clincs to client %s\n", client.getInetAddress().getHostAddress());
            } catch (Exception e) {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            }
        } else if (msg.getClass().equals(ClincEntity.class)) {
            for (int i = 0; i < Clinics.size(); i++) {
                if (Clinics.get(i).getId() == ((ClincEntity) msg).getId()) {
                    session.beginTransaction();
                    Clinics.get(i).setOpen(((ClincEntity) msg).getOpen());
                    Clinics.get(i).setClose(((ClincEntity) msg).getClose());
                    session.save(Clinics.get(i));
                    session.flush();
                    session.getTransaction().commit();
                    System.out.format("Updating all clincs on client %s\n", client.getInetAddress().getHostAddress());
                }
            }
        }

    }

    private static List<ClincEntity> getALLClinics() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ClincEntity> query = builder.createQuery(ClincEntity.class);
        query.from(ClincEntity.class);
        List<ClincEntity> result = session.createQuery(query).getResultList();
        return result;
    }
}
