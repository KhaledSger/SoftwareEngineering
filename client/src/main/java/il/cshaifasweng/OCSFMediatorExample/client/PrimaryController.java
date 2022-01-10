package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button LoginBtn;

	@FXML
	private TextField ID;

	@FXML
	private PasswordField Password;

//	@FXML
//	void Login(ActionEvent event){
//
//	}

	@FXML
	void initialize() {
		assert LoginBtn != null : "fx:id=\"LoginBtn\" was not injected: check your FXML file 'primary.fxml'.";
		assert ID != null : "fx:id=\"ID\" was not injected: check your FXML file 'primary.fxml'.";
		assert Password != null : "fx:id=\"Password\" was not injected: check your FXML file 'primary.fxml'.";

	}

	@FXML
	public void Login(javafx.event.ActionEvent actionEvent) {
		SimpleClient.getClient().LogIn(Integer.parseInt(ID.getText()),Password.getText());
	}
}
