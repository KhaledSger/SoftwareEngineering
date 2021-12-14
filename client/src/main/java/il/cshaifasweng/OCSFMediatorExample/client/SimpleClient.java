package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ClincEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;
	public static List<ClincEntity> ClnicList;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}else if(msg.getClass().equals(ClincEntity.class)){
			EventBus.getDefault().post(new ClinicHandler((ClincEntity) msg));
			ClnicList.add((ClincEntity) msg);
			System.out.println(((ClincEntity) msg).getName());

		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
