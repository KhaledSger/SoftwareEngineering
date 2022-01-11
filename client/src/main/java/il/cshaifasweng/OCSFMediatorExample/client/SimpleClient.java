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
    public int logInFlag = -1;
    public int availableUsers = 0;


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
            availableUsers++;
        } else if (msg.getClass().equals(DoctorEntity.class)) {
            doctorClient = new DoctorClient(this.getHost(), this.getPort(), (DoctorEntity) msg);
            availableUsers++;
        } else if (msg.getClass().equals(NurseEntity.class)) {
            nurseClient = new NurseClient(this.getHost(), this.getPort(), (NurseEntity) msg);
            availableUsers++;
        } else if (msg.getClass().equals(ManagerEntity.class)) {
            managerClient = new ManagerClient(this.getHost(), this.getPort(), (ManagerEntity) msg);
            availableUsers++;
        } else if (msg.getClass().equals(String.class)) {
            if (((String) msg).equals("#Login Success")) {
                logInFlag = 1;
            } else if (((String) msg).equals("#Login Failure")) {
                logInFlag = 2;
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

    public static List<ClinicEntity> getClinicList() {
        return ClinicList;
    }

    public static DoctorClient getDoctorClient() {
        return doctorClient;
    }

    public static NurseClient getNurseClient() {
        return nurseClient;
    }

    public int getLogInFlag() {
        return logInFlag;
    }

    public int getAvailableUsers(){
        return availableUsers;
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
