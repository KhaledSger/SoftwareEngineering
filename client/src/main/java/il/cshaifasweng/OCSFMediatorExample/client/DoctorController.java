
/**
 * Sample Skeleton for 'doctor.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.AppointmentEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.PatientEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class DoctorController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="apps_list"
    private ListView<Text> apps_list; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="patients_list"
    private ListView<Button> patients_list; // Value injected by FXMLLoader

    @FXML // fx:id="view_apps"
    private Button view_apps; // Value injected by FXMLLoader

    @FXML // fx:id="view_patients"
    private Button view_patients; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event)  throws IOException {
        App.setRoot("login");
    }

    @FXML
    void view_apps_handler(ActionEvent event) {  // show each appointment that belongs to the current doctor
        for(AppointmentEntity app : SimpleClient.doctorClient.getReservedAppointment())
        {
            apps_list.getItems().add(new Text(app.getDate().toString()+"-"+app.getPatient().getFirst_name()+" "+app.getPatient().getFamily_name()));
        }
    }

    @FXML
    void view_patients_handler(ActionEvent event) {  // add a button for each daily appointment (that has been confirmed by the appointment control station) that belongs to the doctor
        for(PatientEntity patient : SimpleClient.doc_patients)
        {
            patients_list.getItems().add(new Button(patient.getFirst_name()+" "+patient.getFamily_name()));
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert apps_list != null : "fx:id=\"apps_list\" was not injected: check your FXML file 'doctor.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'doctor.fxml'.";
        assert patients_list != null : "fx:id=\"patients_list\" was not injected: check your FXML file 'doctor.fxml'.";
        assert view_apps != null : "fx:id=\"view_apps\" was not injected: check your FXML file 'doctor.fxml'.";
        assert view_patients != null : "fx:id=\"view_patients\" was not injected: check your FXML file 'doctor.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'doctor.fxml'.";
        welcome_text.setText(SimpleClient.doctorClient.getName());   // doctor's name here

    }

}
