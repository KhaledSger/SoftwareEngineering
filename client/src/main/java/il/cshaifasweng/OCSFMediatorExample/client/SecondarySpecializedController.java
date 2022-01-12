package il.cshaifasweng.OCSFMediatorExample.client;

/**
 * Sample Skeleton for 'secondarySpecialized.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class SecondarySpecializedController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="choose_clinic_button"
    private MenuButton choose_clinic_button; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert choose_clinic_button != null : "fx:id=\"choose_clinic_button\" was not injected: check your FXML file 'secondarySpecialized.fxml'.";

    }

}

