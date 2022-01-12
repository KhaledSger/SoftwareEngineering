package il.cshaifasweng.OCSFMediatorExample.client;

/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="clinics"
	private Button clinics; // Value injected by FXMLLoader

	@FXML // fx:id="otolaryngology_button"
	private MenuItem otolaryngology_button; // Value injected by FXMLLoader

	@FXML // fx:id="skin_button"
	private MenuItem skin_button; // Value injected by FXMLLoader

	@FXML // fx:id="specialized_button"
	private MenuItem specialized_button; // Value injected by FXMLLoader

	@FXML
	void view_clinics(ActionEvent event){
		try {
			App.setRoot("secondary");
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	@FXML
	void otolaryngology_handler(ActionEvent event) {

	}

	@FXML
	void reserve_specialization_handler(ActionEvent event) {

	}

	@FXML
	void skin_handler(ActionEvent event) {
		try {
			App.setRoot("secondary");
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert clinics != null : "fx:id=\"clinics\" was not injected: check your FXML file 'primary.fxml'.";

	}

}

