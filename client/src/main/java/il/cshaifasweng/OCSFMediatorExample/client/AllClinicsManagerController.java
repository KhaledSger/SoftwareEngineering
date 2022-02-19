/**
 * Sample Skeleton for 'AllClinicsManager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.net.URL;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.ClinicEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class AllClinicsManagerController {

    ClinicEntity choosen_clinic;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="skipped_apps_report_btn"
    private Button skipped_apps_report_btn; // Value injected by FXMLLoader

    @FXML // fx:id="treat_num_report_btn"
    private Button treat_num_report_btn; // Value injected by FXMLLoader

    @FXML // fx:id="waiting_time_report_btn"
    private Button waiting_time_report_btn; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML // fx:id="choose_clinic_menu"
    private MenuButton choose_clinic_menu; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    void skipped_apps_report_handler(ActionEvent event) {

    }

    @FXML
    void treat_num_report_handler(ActionEvent event) {

    }

    @FXML
    void waiting_time_report_handler(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'AllClinicsManager.fxml'.";
        assert skipped_apps_report_btn != null : "fx:id=\"skipped_apps_report_btn\" was not injected: check your FXML file 'AllClinicsManager.fxml'.";
        assert treat_num_report_btn != null : "fx:id=\"treat_num_report_btn\" was not injected: check your FXML file 'AllClinicsManager.fxml'.";
        assert waiting_time_report_btn != null : "fx:id=\"waiting_time_report_btn\" was not injected: check your FXML file 'AllClinicsManager.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'AllClinicsManager.fxml'.";
        assert choose_clinic_menu != null : "fx:id=\"choose_clinic_menu\" was not injected: check your FXML file 'AllClinicsManager.fxml'.";
        for(ClinicEntity clinic : SimpleClient.getClinicList())  // adding the clinics to the menu
        {
            if (!choose_clinic_menu.getItems().contains(clinic.getName())){
                choose_clinic_menu.getItems().add(new MenuItem(clinic.getName()));
            }
        }
        for(MenuItem item : choose_clinic_menu.getItems())
        {
            item.setOnAction(actionEvent -> {
                for(ClinicEntity clinic : SimpleClient.getClinicList())
                    if(clinic.getName().equals(item.getText()))
                    {
                         choosen_clinic = clinic;
                    }
            });
        }
    }

}
