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
    public static PatientClient patientClient = null;
    public static DoctorClient doctorClient = null;
    public static NurseClient nurseClient = null;
    public static ManagerClient managerClient = null;


    private SimpleClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        } else if (msg.getClass().equals(ArrayList.class)) {
            ClinicList = ((List<ClinicEntity>) msg);
        } else if (msg.getClass().equals(PatientEntity.class)) {
            patientClient = new PatientClient(this.getHost(), this.getPort(), (PatientEntity) msg);
        } else if (msg.getClass().equals(DoctorEntity.class)) {
            doctorClient = new DoctorClient(this.getHost(), this.getPort(), (DoctorEntity) msg);
        } else if (msg.getClass().equals(NurseEntity.class)) {
            nurseClient = new NurseClient(this.getHost(), this.getPort(), (NurseEntity) msg);
        } else if (msg.getClass().equals(ManagerEntity.class)) {
            managerClient = new ManagerClient(this.getHost(), this.getPort(), (ManagerEntity) msg);
        } else if (msg.getClass().equals(String.class)) {
            if (((String) msg).equals("#Login Success")) {

            } else if (((String) msg).equals("#Login Failure")) {

            }
        }
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }
    public static PatientClient getPatientClient() {

        return patientClient;
    }
    public static ManagerClient getManagerClient() {

        return managerClient;
    }

    public void LogIn(int id, String password){
        UserEntity thisUser = new UserEntity();
        thisUser.setTmpPassword(password);
        thisUser.setId(id);
        try {
            SimpleClient.getClient().sendToServer(thisUser);
        } catch (IOException e) {

            System.out.println("EXCEPTION FROM HERE");
        }
    }
}
