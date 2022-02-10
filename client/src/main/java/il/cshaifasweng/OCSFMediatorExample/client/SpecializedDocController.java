/**
 * Sample Skeleton for 'specializedDoc.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpecializedDocController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="datePickerBtn"
    private DatePicker datePickerBtn; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML // fx:id="clinics_vbox" for adding the clinics that we want to show in it
    private VBox clinics_vbox; // Value injected by FXMLLoader

    @FXML // fx:id="hours_vbox"
    private VBox hours_vbox; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) {

    }

    @FXML
    void datePickerAction(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'specializedDoc.fxml'.";
        assert datePickerBtn != null : "fx:id=\"datePickerBtn\" was not injected: check your FXML file 'specializedDoc.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'specializedDoc.fxml'.";
        assert clinics_vbox != null : "fx:id=\"clinics_vbox\" was not injected: check your FXML file 'specializedDoc.fxml'.";
        assert hours_vbox != null : "fx:id=\"hours_vbox\" was not injected: check your FXML file 'specializedDoc.fxml'.";
        welcome_text.setText(SimpleClient.getPatientClient().getName());   // patient's name here
    }

}
