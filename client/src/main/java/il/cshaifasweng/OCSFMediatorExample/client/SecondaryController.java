/**
 * Sample Skeleton for 'secondary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondaryController {
    static int current = 0;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="closing_hours"
    private TextField closing_hours; // Value injected by FXMLLoader

    @FXML // fx:id="update_button"
    private Button update_button; // Value injected by FXMLLoader

    @FXML // fx:id="option3"
    private MenuItem option3; // Value injected by FXMLLoader

    @FXML // fx:id="opening_hours"
    private TextField opening_hours; // Value injected by FXMLLoader

    @FXML // fx:id="option4"
    private MenuItem option4; // Value injected by FXMLLoader

    @FXML // fx:id="option1"
    private MenuItem option1; // Value injected by FXMLLoader

    @FXML // fx:id="option2"
    private MenuItem option2; // Value injected by FXMLLoader

    @FXML // fx:id="clinic_info"
    private TextField clinic_info; // Value injected by FXMLLoader

    @FXML // fx:id="choose_clinic"
    private MenuButton choose_clinic; // Value injected by FXMLLoader

    @FXML
    public Button closeButton;

    @FXML
    void open_1(ActionEvent event) {
        current = 0;
        clinic_info.setText("Name : " + SimpleClient.ClnicList.get(0).getName() + " ,Open : " + SimpleClient.ClnicList.get(0).getOpen() + " ,Close : " + SimpleClient.ClnicList.get(0).getClose());
    }

    @FXML
    void open_2(ActionEvent event) {
        current = 1;
        clinic_info.setText("Name : " + SimpleClient.ClnicList.get(1).getName() + " ,Open : " + SimpleClient.ClnicList.get(1).getOpen() + " ,Close : " + SimpleClient.ClnicList.get(1).getClose());
    }

    @FXML
    void open_3(ActionEvent event) {
        current = 2;
        clinic_info.setText("Name : " + SimpleClient.ClnicList.get(2).getName() + " ,Open : " + SimpleClient.ClnicList.get(2).getOpen() + " ,Close : " + SimpleClient.ClnicList.get(2).getClose());
    }

    @FXML
    void open_4(ActionEvent event) {
        current = 3;
        clinic_info.setText("Name : " + SimpleClient.ClnicList.get(3).getName() + " ,Open : " + SimpleClient.ClnicList.get(3).getOpen() + " ,Close : " + SimpleClient.ClnicList.get(3).getClose());
    }

    @FXML
    void update_hours(ActionEvent event) {
        String opening = opening_hours.getText();
        String closing = closing_hours.getText();
        if (opening != "")
            SimpleClient.ClnicList.get(current).setOpen(opening);
        if (closing != "")
            SimpleClient.ClnicList.get(current).setClose(closing);
        if (closing != "" || opening != "") {
            try {
                SimpleClient.getClient().sendToServer(SimpleClient.ClnicList.get(current));
                SimpleClient.getClient().sendToServer("#GetAllClinics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        clinic_info.setText("Name : " + SimpleClient.ClnicList.get(current).getName() + " Open : " + SimpleClient.ClnicList.get(current).getOpen() + " Close : " + SimpleClient.ClnicList.get(current).getClose());
        opening_hours.setText("");
        closing_hours.setText("");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert closing_hours != null : "fx:id=\"closing_hours\" was not injected: check your FXML file 'secondary.fxml'.";
        assert update_button != null : "fx:id=\"update_button\" was not injected: check your FXML file 'secondary.fxml'.";
        assert option3 != null : "fx:id=\"option3\" was not injected: check your FXML file 'secondary.fxml'.";
        assert opening_hours != null : "fx:id=\"opening_hours\" was not injected: check your FXML file 'secondary.fxml'.";
        assert option4 != null : "fx:id=\"option4\" was not injected: check your FXML file 'secondary.fxml'.";
        assert option1 != null : "fx:id=\"option1\" was not injected: check your FXML file 'secondary.fxml'.";
        assert option2 != null : "fx:id=\"option2\" was not injected: check your FXML file 'secondary.fxml'.";
        assert clinic_info != null : "fx:id=\"clinic_info\" was not injected: check your FXML file 'secondary.fxml'.";
        assert choose_clinic != null : "fx:id=\"choose_clinic\" was not injected: check your FXML file 'secondary.fxml'.";

    }

    @FXML
    public void handleCloseButtonAction(ActionEvent actionEvent) {
        try {
            SimpleClient.getClient().sendToServer("#CloseSession");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
