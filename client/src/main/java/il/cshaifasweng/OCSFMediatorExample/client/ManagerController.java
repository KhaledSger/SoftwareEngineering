/**
 * Sample Skeleton for 'manager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ManagerController {
    // set manager's name in initialize()
    /*
        add menu items to doctor menu dynamically like this:
        // choose_doctor_menu.getItems().add(new MenuItem("doctor's name"));
    */


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="choose_clinic_menu"
    private MenuButton choose_clinic_menu; // Value injected by FXMLLoader

    @FXML // fx:id="choose_doctor__menu"
    private MenuButton choose_doctor_menu; // Value injected by FXMLLoader

    @FXML // fx:id="choose_vaccine_menu"
    private MenuButton choose_vaccine_menu; // Value injected by FXMLLoader

    @FXML // fx:id="clinic_close_txt"
    private TextField clinic_close_txt; // Value injected by FXMLLoader

    @FXML // fx:id="clinic_current_hours"
    private TextField clinic_current_hours; // Value injected by FXMLLoader

    @FXML // fx:id="clinic_open_txt"
    private TextField clinic_open_txt; // Value injected by FXMLLoader

    @FXML // fx:id="covid_test_close_txt"
    private TextField covid_test_close_txt; // Value injected by FXMLLoader

    @FXML // fx:id="covid_test_current_hours"
    private TextField covid_test_current_hours; // Value injected by FXMLLoader

    @FXML // fx:id="covid_test_open_txt"
    private TextField covid_test_open_txt; // Value injected by FXMLLoader

    @FXML // fx:id="covid_vaccine_btn"
    private MenuItem covid_vaccine_btn; // Value injected by FXMLLoader

    @FXML // fx:id="doctor_close_txt"
    private TextField doctor_close_txt; // Value injected by FXMLLoader

    @FXML // fx:id="doctor_current_hours"
    private TextField doctor_current_hours; // Value injected by FXMLLoader

    @FXML // fx:id="doctor_open_txt"
    private TextField doctor_open_txt; // Value injected by FXMLLoader

    @FXML // fx:id="flu_vaccine_btn"
    private MenuItem flu_vaccine_btn; // Value injected by FXMLLoader

    @FXML // fx:id="skipped_apps_report_btn"
    private Button skipped_apps_report_btn; // Value injected by FXMLLoader

    @FXML // fx:id="treat_num_report_btn"
    private Button treat_num_report_btn; // Value injected by FXMLLoader

    @FXML // fx:id="update_clinics_btn"
    private Button update_clinics_btn; // Value injected by FXMLLoader

    @FXML // fx:id="update_covid_test_btn"
    private Button update_covid_test_btn; // Value injected by FXMLLoader

    @FXML // fx:id="update_doctor_btn"
    private Button update_doctor_btn; // Value injected by FXMLLoader

    @FXML // fx:id="update_vaccine_btn"
    private Button update_vaccine_btn; // Value injected by FXMLLoader

    @FXML // fx:id="vaccine_close_txt"
    private TextField vaccine_close_txt; // Value injected by FXMLLoader

    @FXML // fx:id="vaccine_current_hours"
    private TextField vaccine_current_hours; // Value injected by FXMLLoader

    @FXML // fx:id="vaccine_open_txt"
    private TextField vaccine_open_txt; // Value injected by FXMLLoader

    @FXML // fx:id="waiting_time_report_btn"
    private Button waiting_time_report_btn; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) {

    }

    @FXML
    void covid_vaccine_handler(ActionEvent event) {

    }

    @FXML
    void flu_vaccine_handler(ActionEvent event) {

    }

    @FXML
    void skipped_apps_report_handler(ActionEvent event) {

    }

    @FXML
    void treat_num_report_handler(ActionEvent event) {

    }

    @FXML
    void update_clinics_handler(ActionEvent event) {

    }

    @FXML
    void update_covid_test_handler(ActionEvent event) {

    }

    @FXML
    void update_doctors_handler(ActionEvent event) {

    }

    @FXML
    void update_vaccine_handler(ActionEvent event) {

    }

    @FXML
    void waiting_time_report_handler(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'manager.fxml'.";
        assert choose_clinic_menu != null : "fx:id=\"choose_clinic_menu\" was not injected: check your FXML file 'manager.fxml'.";
        assert choose_doctor_menu != null : "fx:id=\"choose_doctor__menu\" was not injected: check your FXML file 'manager.fxml'.";
        assert choose_vaccine_menu != null : "fx:id=\"choose_vaccine_menu\" was not injected: check your FXML file 'manager.fxml'.";
        assert clinic_close_txt != null : "fx:id=\"clinic_close_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert clinic_current_hours != null : "fx:id=\"clinic_current_hours\" was not injected: check your FXML file 'manager.fxml'.";
        assert clinic_open_txt != null : "fx:id=\"clinic_open_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert covid_test_close_txt != null : "fx:id=\"covid_test_close_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert covid_test_current_hours != null : "fx:id=\"covid_test_current_hours\" was not injected: check your FXML file 'manager.fxml'.";
        assert covid_test_open_txt != null : "fx:id=\"covid_test_open_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert covid_vaccine_btn != null : "fx:id=\"covid_vaccine_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert doctor_close_txt != null : "fx:id=\"doctor_close_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert doctor_current_hours != null : "fx:id=\"doctor_current_hours\" was not injected: check your FXML file 'manager.fxml'.";
        assert doctor_open_txt != null : "fx:id=\"doctor_open_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert flu_vaccine_btn != null : "fx:id=\"flu_vaccine_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert skipped_apps_report_btn != null : "fx:id=\"skipped_apps_report_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert treat_num_report_btn != null : "fx:id=\"treat_num_report_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert update_clinics_btn != null : "fx:id=\"update_clinics_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert update_covid_test_btn != null : "fx:id=\"update_covid_test_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert update_doctor_btn != null : "fx:id=\"update_doctor_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert update_vaccine_btn != null : "fx:id=\"update_vaccine_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert vaccine_close_txt != null : "fx:id=\"vaccine_close_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert vaccine_current_hours != null : "fx:id=\"vaccine_current_hours\" was not injected: check your FXML file 'manager.fxml'.";
        assert vaccine_open_txt != null : "fx:id=\"vaccine_open_txt\" was not injected: check your FXML file 'manager.fxml'.";
        assert waiting_time_report_btn != null : "fx:id=\"waiting_time_report_btn\" was not injected: check your FXML file 'manager.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'manager.fxml'.";
        //  welcome_text.setText("name");  // add manager's name here
    }

}
