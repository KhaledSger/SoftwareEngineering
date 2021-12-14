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

	public SimpleServer(int port) {
		super(port);

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
		System.out.format(msgString);

		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (msgString.equals("#ClincInit")){
			try {
				session = getSessionFactory().openSession();
				session.beginTransaction();
				ClincEntity clinc1 = new ClincEntity("Haifa clinic","12:00","18:00", new String[]{"aaa", "bbb", "ccc"});
				session.save(clinc1);
				ClincEntity clinc2 = new ClincEntity("Acre clinic","12:00","18:00", new String[]{"bbb", "ddd", "ccc"});
				session.save(clinc2);
				ClincEntity clinc3 = new ClincEntity("Tel-Aviv clinic","12:00","18:00", new String[]{"aaa", "eee", "ddd"});
				session.save(clinc3);
				ClincEntity clinc4 = new ClincEntity("Abla clinic");
				session.save(clinc4);
				session.flush();
				session.getTransaction().commit();
			}
				catch (Exception e) {
					if (session!= null)
						{
							session.getTransaction().rollback();
						}

				}finally {
					if (session != null ){
						session.close();
					}
				}
		}else if (msgString.equals("#GetAllClinics")){
				List<ClincEntity> clnics = getALLClinics();
			try {
				System.out.println("************** " +clnics.size() + "*************\n");
				for (int i = 0; i <clnics.size(); i++){
					client.sendToClient(clnics.get(i));
					clnics.get(i).getName();
				}
				System.out.format("Sent all clincs to client %s\n", client.getInetAddress().getHostAddress());
			}
			catch (Exception e) {
				if (session!= null)
				{
					session.getTransaction().rollback();
				}

			}finally {
				if (session != null ){
					session.close();
				}
			}
		}
	}

	private static List<ClincEntity> getALLClinics()
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<ClincEntity> query = builder.createQuery(ClincEntity.class);
		query.from(ClincEntity.class);
		List<ClincEntity> result = session.createQuery(query).getResultList();
		return  result;
	}
}
