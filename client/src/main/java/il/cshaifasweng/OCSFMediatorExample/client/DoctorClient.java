package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.DoctorEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import org.greenrobot.eventbus.EventBus;

public class DoctorClient  extends AbstractClient {
    private static DoctorClient client = null;
    public static DoctorEntity Doctor = null;

    public DoctorClient(String host, int port, DoctorEntity Doctor) {
        super(host, port);
        this.Doctor = Doctor;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        } else if (msg.getClass().equals(DoctorEntity.class)) {
            Doctor = (DoctorEntity) msg;
        }else if(msg.getClass().equals(String.class))
        {
            System.out.println("DoctorClient string");
        }
    }

    public static DoctorClient getClient() {
        if (client == null) {
            client = new DoctorClient("localhost", 3000, Doctor);
        }
        return client;
    }

}