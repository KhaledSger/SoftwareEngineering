/**
 * Sample Skeleton for 'covidTest.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class CovidTestController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="first_no"
    private ToggleButton first_no; // Value injected by FXMLLoader

    @FXML // fx:id="first_yes"
    private ToggleButton first_yes; // Value injected by FXMLLoader

    @FXML // fx:id="second_no"
    private ToggleButton second_no; // Value injected by FXMLLoader

    @FXML // fx:id="second_yes"
    private ToggleButton second_yes; // Value injected by FXMLLoader

    @FXML // fx:id="submit_btn"
    private Button submit_btn; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
        App.setRoot("patient");
    }

    @FXML
    void first_no_action(ActionEvent event) {
    }

    @FXML
    void first_yes_action(ActionEvent event) {
    }

    @FXML
    void second_no_action(ActionEvent event) {

    }

    @FXML
    void second_yes_action(ActionEvent event) {

    }

    @FXML
    void submit_handler(ActionEvent event) throws IOException {
        App.setRoot("covidTestApps");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'covidTest.fxml'.";
        assert first_no != null : "fx:id=\"first_no\" was not injected: check your FXML file 'covidTest.fxml'.";
        assert first_yes != null : "fx:id=\"first_yes\" was not injected: check your FXML file 'covidTest.fxml'.";
        assert second_no != null : "fx:id=\"second_no\" was not injected: check your FXML file 'covidTest.fxml'.";
        assert second_yes != null : "fx:id=\"second_yes\" was not injected: check your FXML file 'covidTest.fxml'.";
        assert submit_btn != null : "fx:id=\"submit_btn\" was not injected: check your FXML file 'covidTest.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'covidTest.fxml'.";
        //  welcome_text.setText();   add patient's name here
    }

}
