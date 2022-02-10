/**
 * Sample Skeleton for 'ViewAppointments.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class ViewAppointmentsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="cancelAppBtn"
    private Button cancelAppBtn; // Value injected by FXMLLoader

    @FXML // fx:id="pane"
    private ScrollPane pane; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) {
        try{  App.setRoot("patient"); }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelAppAction(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'ViewAppointments.fxml'.";
        assert cancelAppBtn != null : "fx:id=\"cancelAppBtn\" was not injected: check your FXML file 'ViewAppointments.fxml'.";
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'ViewAppointments.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'ViewAppointments.fxml'.";
        welcome_text.setText(SimpleClient.getPatientClient().getName());   // patient's name here
    }

}
