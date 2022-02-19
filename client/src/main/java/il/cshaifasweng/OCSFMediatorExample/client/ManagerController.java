/**
 * Sample Skeleton for 'manager.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.ClinicEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.DoctorClinicEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.DoctorEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.css.CSSStyleSheet;

import javax.print.Doc;

public class ManagerController {
    // set manager's name in initialize()
    /*
        add menu items to doctor menu dynamically like this:
        // choose_doctor_menu.getItems().add(new MenuItem("doctor's name"));
    */

    private ClinicEntity choosen_clinic = SimpleClient.getManagerClient().getClinic();
    private DoctorClinicEntity choosen_doctor_clinic;

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

    @FXML // fx:id="clinic_text"
    private Text clinic_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
        App.setRoot("login");

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
    void update_clinics_handler(ActionEvent event) throws IOException {
        if(!clinic_open_txt.getText().equals(""))
            choosen_clinic.setOpen(clinic_open_txt.getText());
        if(!clinic_close_txt.getText().equals(""))
            choosen_clinic.setClose(clinic_close_txt.getText());
        clinic_current_hours.setText(choosen_clinic.getOpen() + "-" + choosen_clinic.getClose());
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    String.format("updated successfully!")
            );
            alert.show();
        });
        SimpleClient.getClient().sendToServer(choosen_clinic);
        clinic_open_txt.setText("");
        clinic_close_txt.setText("");
    }

    @FXML
    void update_covid_test_handler(ActionEvent event) {

    }

    @FXML
    void update_doctors_handler(ActionEvent event) {
//        ArrayList<String> day_hours = new ArrayList<String>();
//        for(int i=0 ; i<5;i++)
//        {
//            if(!doctor_open_txt.getText().equals("") && !doctor_close_txt.getText().equals(""))
//                day_hours.set(i,doctor_open_txt.getText()+"-"+doctor_close_txt.getText()+"@&%@");
//            else if(doctor_open_txt.getText().equals("") && !doctor_close_txt.getText().equals(""))
//        }
//        for(int i=5;i<7;i++)
//            day_hours.set(i,"00:00-00:00@&%@");
//        doctor_current_hours.setText(choosen_doctor_clinic.getDay_hours());
//        Platform.runLater(() -> {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
//                    String.format("updated successfully!")
//            );
//            alert.show();
//        });
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
        assert clinic_text != null : "fx:id=\"clinic_text\" was not injected: check your FXML file 'manager.fxml'.";
        welcome_text.setText(SimpleClient.managerClient.getName());  // add manager's name here
        clinic_text.setText(SimpleClient.getManagerClient().getClinic().getName());
        clinic_current_hours.setText(choosen_clinic.getOpen() + "-" + choosen_clinic.getClose());
       // covid_test_current_hours.setText();
        for(ClinicEntity clinic : SimpleClient.getClinicList())  // adding the clinics and doctors to the menus
        {
            for(DoctorClinicEntity doc_clinic : clinic.getDoctorClinicEntities())
            {
                if(!(choose_doctor_menu.getItems().contains(doc_clinic.getDoctor().getFirst_name() + " " +doc_clinic.getDoctor().getFamily_name())))
                choose_doctor_menu.getItems().add(new MenuItem(doc_clinic.getDoctor().getFirst_name() + " " +doc_clinic.getDoctor().getFamily_name()));
            }
        }
//        for(MenuItem item : choose_clinic_menu.getItems())
//        {
//            item.setOnAction(actionEvent -> {
//                for(ClinicEntity clinic : SimpleClient.getClinicList())
//                    if(clinic.getName().equals(item.getText()))
//                    {
//                        clinic_current_hours.setText(clinic.getOpen() + "-" + clinic.getClose());
//                        choosen_clinic=clinic;
//                        choose_clinic_menu.setText(clinic.getName());
//                    }
//            });
//        }
        for(MenuItem item : choose_doctor_menu.getItems())
        {
            item.setOnAction(actionEvent -> {
                for(ClinicEntity clinic : SimpleClient.getClinicList())
                {
                    for(DoctorClinicEntity doc_clinic : clinic.getDoctorClinicEntities())
                    {
                        if((doc_clinic.getDoctor().getFirst_name() +" "+ doc_clinic.getDoctor().getFamily_name()).equals(item.getText()))
                            doctor_current_hours.setText(doc_clinic.getDay_hours());
                            choosen_doctor_clinic = doc_clinic;
                            choose_doctor_menu.setText(doc_clinic.getDoctor().getFirst_name()+" "+ doc_clinic.getDoctor().getFamily_name());
                    }
                }
            });
        }
    }

}
