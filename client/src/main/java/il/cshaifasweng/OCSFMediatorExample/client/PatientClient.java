package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PatientClient  extends AbstractClient {
    private static PatientClient client = null;
    public static PatientEntity Patient = null;

    public PatientClient(String host, int port,PatientEntity patient) {
        super(host, port);
       this.Patient=patient;
    }
    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        } else if (msg.getClass().equals(PatientEntity.class)){
           Patient = (PatientEntity) msg;
        }else if(msg.getClass().equals(String.class))
        {
            System.out.println("PatientClient string");
        }
    }

    public static PatientClient getClient() {
        if (client == null) {
            client = new PatientClient("localhost", 3000,Patient);
        }
        return client;
    }

    public String getName()
    {
        return (this.Patient.getFirst_name() + " " + this.Patient.getFamily_name());
    }

    public int getId()
    {
        return this.Patient.getId();
    }

}
