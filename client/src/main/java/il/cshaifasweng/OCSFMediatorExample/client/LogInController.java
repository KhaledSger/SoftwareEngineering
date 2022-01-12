package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button DoctorBtn;

    @FXML
    private Button PatientBtn;

    @FXML
    private Button ManagerBtn;

    @FXML
    private Button Nursebtn;

    @FXML
    void initialize() {
        assert DoctorBtn != null : "fx:id=\"DoctorBtn\" was not injected: check your FXML file 'login.fxml'.";
        assert PatientBtn != null : "fx:id=\"PatientBtn\" was not injected: check your FXML file 'login.fxml'.";
        assert ManagerBtn != null : "fx:id=\"ManagerBtn\" was not injected: check your FXML file 'login.fxml'.";
        assert Nursebtn != null : "fx:id=\"Nursebtn\" was not injected: check your FXML file 'login.fxml'.";
        if(SimpleClient.getNurseClient() == null){
            Nursebtn.setDisable(true);
        }
        if(SimpleClient.getDoctorClient() == null){
            DoctorBtn.setDisable(true);
        }
        if(SimpleClient.getManagerClient() == null){
            ManagerBtn.setDisable(true);
        }
        if(SimpleClient.getPatientClient() == null){
            PatientBtn.setDisable(true);
        }
    }

    public void NurseA(javafx.event.ActionEvent actionEvent) throws IOException {
        App.setRoot("nurse");
        SimpleClient.getClient().setCurrentUser(1);
    }

    public void PatientA(javafx.event.ActionEvent actionEvent) throws IOException {
        App.setRoot("patient");
        SimpleClient.getClient().setCurrentUser(0);
    }

    public void DoctorA(javafx.event.ActionEvent actionEvent) throws IOException {
        App.setRoot("doctor");
        SimpleClient.getClient().setCurrentUser(2);
    }

    public void ManagerA(javafx.event.ActionEvent actionEvent) throws IOException {
        App.setRoot("manager");
        SimpleClient.getClient().setCurrentUser(3);
    }
}
