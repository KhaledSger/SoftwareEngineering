package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
