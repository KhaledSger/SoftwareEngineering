/**
 * Sample Skeleton for 'doctor.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.*;

public class NurseController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="view_apps"
    private Button view_apps; // Value injected by FXMLLoader

    @FXML // fx:id="view_patients"
    private Button view_patients; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML // fx:id="apps_area"
    private TextArea apps_area; // Value injected by FXMLLoader

    @FXML // fx:id="patients_area"
    private TextArea patients_area; // Value injected by FXMLLoader


    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void view_apps_handler(ActionEvent event) { // add doctor's appointments dynamically
        //apps_area.setText("appointment information (date , clinic, patient's name");
    }

    @FXML
    void view_patients_handler(ActionEvent event) { // add doctor's patients dynamically
        //patients_area.setText("patient's name");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'doctor.fxml'.";
        assert view_apps != null : "fx:id=\"view_apps\" was not injected: check your FXML file 'doctor.fxml'.";
        assert view_patients != null : "fx:id=\"view_patients\" was not injected: check your FXML file 'doctor.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'doctor.fxml'.";
        assert apps_area != null : "fx:id=\"apps_area\" was not injected: check your FXML file 'doctor.fxml'.";
        assert patients_area != null : "fx:id=\"patients_area\" was not injected: check your FXML file 'doctor.fxml'.";
        welcome_text.setText(SimpleClient.nurseClient.getName());   // nurse's name here
    }

}
