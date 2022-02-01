/**
 * Sample Skeleton for 'GreenPass.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GreenPassController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="effective_date"
    private Text effective_date; // Value injected by FXMLLoader

    @FXML // fx:id="expiration_date"
    private Text expiration_date; // Value injected by FXMLLoader

    @FXML // fx:id="id_text"
    private Text id_text; // Value injected by FXMLLoader

    @FXML
    private AnchorPane anchor;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert effective_date != null : "fx:id=\"effective_date\" was not injected: check your FXML file 'GreenPass.fxml'.";
        assert expiration_date != null : "fx:id=\"expiration_date\" was not injected: check your FXML file 'GreenPass.fxml'.";
        assert id_text != null : "fx:id=\"id_text\" was not injected: check your FXML file 'GreenPass.fxml'.";
        assert anchor != null : "fx:id=\"anchor\" was not injected: check your FXML file 'GreenPass.fxml'.";
        // id_text.setText(); set patient's id here
        // expiration_date.setText(); set patient's vaccine expiration date here
        // effective_date.setText(); set patient's vaccine effective date here
    }

}
