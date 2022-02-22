
/**
 * Sample Skeleton for 'magnetic_card.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

        import java.io.IOException;
        import java.lang.reflect.Array;
        import java.net.URL;
        import java.time.LocalDateTime;
        import java.util.*;

        import com.mysql.cj.x.protobuf.Mysqlx;
        import il.cshaifasweng.OCSFMediatorExample.entities.*;
        import javafx.application.Platform;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.scene.text.Text;

public class MagneticCardController {

    private PatientEntity chosen_patient = MagneticCardLoginController.chosen_patient;
    private ClinicEntity chosen_clinic = MagneticCardLoginController.chosen_clinic;
    private int has_app_flag=0;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="appointment_display_listView"
    private TextArea appointment_display_listView; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="doctor_app_btn"
    private Button doctor_app_btn; // Value injected by FXMLLoader

    @FXML // fx:id="doctor_listView"
    private TextArea doctor_listView; // Value injected by FXMLLoader

    @FXML // fx:id="nurse_lab_app_btn"
    private Button nurse_lab_app_btn; // Value injected by FXMLLoader

    @FXML // fx:id="nurse_listView"
    private TextArea nurse_listView; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
        App.setRoot("magnetic_card_Login");
    }

    @FXML
    void nurse_lab_app_action(ActionEvent event) {
        if(!(chosen_patient.getClinic().getId()==(chosen_clinic.getId())))
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        String.format("You are not a member of this clinic!")
                );
                alert.show();
            });
        }
        else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format("choose:"),new ButtonType("Lab"),new ButtonType("Nurse")
                );
                Optional<ButtonType> lab_nurse =  alert.showAndWait();
                System.out.println("lab nurse= "+lab_nurse.get().getText());
                if(lab_nurse.get().getText() == "Nurse")
                {
                    try {
                        Reserve_nurse_app();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Reserve_lab_app();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @FXML
    void doctor_app_action(ActionEvent event) {
        LocalDateTime now=LocalDateTime.now();
            for(DoctorClinicEntity doc_clinic : chosen_clinic.getDoctorClinicEntities())
            {
                for(AppointmentEntity app : doc_clinic.getDoctor().getAppointments())
                {
                    if (app.isReserved()) {
                        if (app.getPatient().getId() == chosen_patient.getId()) {
                            SimpleClient.doc_patients.add(chosen_patient);
                            has_app_flag=1;
                            SimpleClient.next_doc_appointment += 1;
                            doctor_listView.setText("appointment for Doctor: " + doc_clinic.getDoctor().getFirst_name() + " " + doc_clinic.getDoctor().getFamily_name() + "\n Appointment Time: " + app.getDate().getHour() + ":" + app.getDate().getMinute() + "\n Appointment number: " + SimpleClient.next_doc_appointment);
                            if(now.isBefore(app.getDate())) // the patient is not late, and he needs to enter to the reserved time
                            {
                                app.setActual_date(app.getDate());
                            }
                            else  //the patient is late, so he needs to enter after the current patients
                            {
                                ArrayList<AppointmentEntity> appointments = (ArrayList<AppointmentEntity>) doc_clinic.getDoctor().getAppointments().stream().toList();
                                for(AppointmentEntity app1 : doc_clinic.getDoctor().getAppointments())
                                {
                                    if(app1.getDate().isAfter(app.getDate()))
                                    {
                                        app.setActual_date(app1.getDate().plusMinutes(20));
                                        try {
                                            SimpleClient.getClient().sendToServer("#updateAppsForDoc:"+app.getId());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        if(has_app_flag==0)
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        String.format("You don't have an Appointment!")
                );
                alert.show();
            });
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert appointment_display_listView != null : "fx:id=\"appointment_display_listView\" was not injected: check your FXML file 'magnetic_card.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'magnetic_card.fxml'.";
        assert doctor_app_btn != null : "fx:id=\"doctor_app_btn\" was not injected: check your FXML file 'magnetic_card.fxml'.";
        assert doctor_listView != null : "fx:id=\"doctor_listView\" was not injected: check your FXML file 'magnetic_card.fxml'.";
        assert nurse_lab_app_btn != null : "fx:id=\"nurse_lab_app_btn\" was not injected: check your FXML file 'magnetic_card.fxml'.";
        assert nurse_listView != null : "fx:id=\"nurse_listView\" was not injected: check your FXML file 'magnetic_card.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'magnetic_card.fxml'.";
        welcome_text.setText(MagneticCardLoginController.chosen_patient.getFirst_name() +" "+ MagneticCardLoginController.chosen_patient.getFamily_name()); //patient's name

    }

    private void Reserve_nurse_app() throws IOException
    {
        SimpleClient.next_nurse_appointment+=1;
        SimpleClient.nurse_patients.add(chosen_patient.getId());
        nurse_listView.setText("appointment for: nurse\n appointment number: "+ SimpleClient.next_nurse_appointment);
        SimpleClient.getClient().sendToServer("#increase nurse app:"+chosen_clinic.getId());
    }
    private void Reserve_lab_app() throws IOException
    {
        LocalDateTime now=LocalDateTime.now();
        if(now.getHour() > 7 && now.getHour() < 10 )
        {
            SimpleClient.next_lab_appointment+=1;
            SimpleClient.nurse_patients.add(chosen_patient.getId());
            nurse_listView.setText("appointment for: lab\n appointment number: "+ SimpleClient.next_lab_appointment);
            SimpleClient.getClient().sendToServer("#increase lab app:"+chosen_clinic.getId());
        }
        else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        String.format("lab appointments are not available at this time\n please arrive again between 8:00 - 10:00!")
                );
                alert.show();
            });
        }
    }

}

