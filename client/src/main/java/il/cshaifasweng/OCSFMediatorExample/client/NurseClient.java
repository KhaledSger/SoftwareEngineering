package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.NurseEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import org.greenrobot.eventbus.EventBus;

public class NurseClient  extends AbstractClient {
    private static NurseClient client = null;
    public static NurseEntity Nurse = null;

    public NurseClient(String host, int port,NurseEntity Nurse) {
        super(host, port);
        this.Nurse=Nurse;
    }
    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        } else if (msg.getClass().equals(NurseEntity.class)){
            Nurse = (NurseEntity) msg;
        }else if(msg.getClass().equals(String.class))
        {
            System.out.println("NurseClient string");
        }
    }

    public static NurseClient getClient() {
        if (client == null) {
            client = new NurseClient("localhost", 3000,Nurse);
        }
        return client;
    }


}