package il.cshaifasweng.OCSFMediatorExample.client;

import com.mysql.cj.xdevapi.Client;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.LabWorkerEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.NurseEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import org.greenrobot.eventbus.EventBus;

public class LabWorkerClient  extends AbstractClient {
    private static LabWorkerClient client = null;
    public static LabWorkerEntity LabWorker = null;

    public LabWorkerClient(String host, int port,LabWorkerEntity LabWorker) {
        super(host, port);
        this.LabWorker=LabWorker;
    }
    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        } else if (msg.getClass().equals(LabWorkerEntity.class)){
            LabWorker = (LabWorkerEntity) msg;
        }else if(msg.getClass().equals(String.class))
        {
            System.out.println("LabWorkerClient string");
        }
    }

    public static LabWorkerClient getClient() {
        if (client == null) {
            client = new LabWorkerClient("localhost", 3000,LabWorker);
        }
        return client;
    }

    public String getName()
    {
        return (this.LabWorker.getFirst_name() + " " + this.LabWorker.getFamily_name());
    }

}