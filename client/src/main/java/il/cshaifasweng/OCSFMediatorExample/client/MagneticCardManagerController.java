/**
 * Sample Skeleton for 'magneticCardManager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MagneticCardManagerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="close_station_btn"
    private Button close_station_btn; // Value injected by FXMLLoader

    @FXML // fx:id="open_station_btn"
    private Button open_station_btn; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void close_station_action(ActionEvent event) { //disabling the button that open the control station window
        PrimaryController.DisableControl_station_btn();
    }

    @FXML
    void open_station_action(ActionEvent event) {  //enabling the button that open the control station window
        PrimaryController.EnableControl_station_btn();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'magneticCardManager.fxml'.";
        assert close_station_btn != null : "fx:id=\"close_station_btn\" was not injected: check your FXML file 'magneticCardManager.fxml'.";
        assert open_station_btn != null : "fx:id=\"open_station_btn\" was not injected: check your FXML file 'magneticCardManager.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'magneticCardManager.fxml'.";
    }

}
