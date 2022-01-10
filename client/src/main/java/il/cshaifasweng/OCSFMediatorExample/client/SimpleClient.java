package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SimpleClient extends AbstractClient {

    private static SimpleClient client = null;
    public static List<ClinicEntity> ClinicList;
    public static PatientEntity thisPatient;
    public static DoctorEntity thisDoctor;
    public static ManagerEntity thisManger;
    public static NurseEntity thisNurse;

    private SimpleClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        } else if (msg.getClass().equals(ArrayList.class)) {
            ClinicList = ((List<ClinicEntity>) msg);
        } else if (msg.getClass().equals(PatientEntity.class)){
            thisPatient = (PatientEntity) msg;
        } else if (msg.getClass().equals(DoctorEntity.class)){
            thisDoctor = (DoctorEntity) msg;
        } else if (msg.getClass().equals(NurseEntity.class)){
            thisNurse = (NurseEntity) msg;
        }else if (msg.getClass().equals(ManagerEntity.class)){
            thisManger = (ManagerEntity) msg;
        }
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }

    public void LogIn(int id, String password) throws IOException {
        UserEntity thisUser = new UserEntity();
        thisUser.setTmpPassword(password);
        thisUser.setId(id);
        SimpleClient.getClient().sendToServer(thisUser);
    }
}
