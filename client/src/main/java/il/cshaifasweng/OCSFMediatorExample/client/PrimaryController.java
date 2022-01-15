package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimaryController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button LoginBtn;

	@FXML
	private TextField ID;

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
			 SimpleClient.getClient().sendToServer("#getALLClinics");
 			App.setRoot("secondary");
 		}catch (IOException e){
 			e.printStackTrace();
 		}

 	}

	@FXML
	private PasswordField Password;

  
	@FXML
	private Button NurseBtn;

	@FXML
	void initialize() {
		assert LoginBtn != null : "fx:id=\"LoginBtn\" was not injected: check your FXML file 'primary.fxml'.";
		assert ID != null : "fx:id=\"ID\" was not injected: check your FXML file 'primary.fxml'.";
		assert Password != null : "fx:id=\"Password\" was not injected: check your FXML file 'primary.fxml'.";

	}

	@FXML
	public void Login(javafx.event.ActionEvent actionEvent) throws IOException {
		String regex = "[0-9]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ID.getText());
		if (m.matches()) {
			SimpleClient.getClient().LogIn(Integer.parseInt(ID.getText()), Password.getText());
			while (SimpleClient.getClient().logInFlag == -1) {
				ProgressBar pb = new ProgressBar(0.6);
				ProgressBar pi = new ProgressBar(0.6);
			}
			if (SimpleClient.getClient().logInFlag == 1) {
				 if(SimpleClient.getClient().getAvailableUsers() < 1){
				 	SimpleClient.getClient().logInFlag = -1;
				 }else if(SimpleClient.getClient().getAvailableUsers() == 1){
				 	App.setRoot("patient");
					 SimpleClient.getClient().setCurrentUser(0);
				 }else{
				 	App.setRoot("login");
				 }
			} else {
				Password.setText("");
				SimpleClient.getClient().logInFlag = -1;
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.ERROR,
							String.format("Incorrect Id or Password, try again")
					);
					alert.show();
				});
			}
		} else {
			ID.setText("");
			Password.setText("");
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.ERROR,
						String.format("ID should contain only numbers, try again")
				);
				alert.show();
			});
		}
	}
}
