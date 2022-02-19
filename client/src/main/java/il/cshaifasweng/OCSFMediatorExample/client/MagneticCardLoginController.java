/**
 * Sample Skeleton for 'magnetic_card_Login.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MagneticCardLoginController {
    public static ClinicEntity chosen_clinic;
    public static PatientEntity chosen_patient=null;
    public static ManagerEntity chosen_manager=null;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="ID"
    private TextField ID; // Value injected by FXMLLoader

    @FXML // fx:id="Password"
    private PasswordField Password; // Value injected by FXMLLoader

    @FXML // fx:id="list_view"
    private ListView<Button> list_view; // Value injected by FXMLLoader

    @FXML // fx:id="pc_appointment_btn"
    private Button pc_appointment_btn; // Value injected by FXMLLoader

    @FXML // fx:id="choose_clinic_btn"
    private MenuButton choose_clinic_btn; // Value injected by FXMLLoader

    @FXML
    void pc_appointment_action(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert ID != null : "fx:id=\"ID\" was not injected: check your FXML file 'magnetic_card_Login.fxml'.";
        assert Password != null : "fx:id=\"Password\" was not injected: check your FXML file 'magnetic_card_Login.fxml'.";
        assert list_view != null : "fx:id=\"list_view\" was not injected: check your FXML file 'magnetic_card_Login.fxml'.";
        assert pc_appointment_btn != null : "fx:id=\"pc_appointment_btn\" was not injected: check your FXML file 'magnetic_card_Login.fxml'.";
        assert choose_clinic_btn != null : "fx:id=\"choose_clinic_btn\" was not injected: check your FXML file 'magnetic_card_Login.fxml'.";

        for(ClinicEntity clinic : SimpleClient.getClinicList())  // adding the clinics to the menu
        {
            if(!choose_clinic_btn.getItems().contains(clinic.getName())) {
                choose_clinic_btn.getItems().add(new MenuItem(clinic.getName()));
            }
        }
        for(MenuItem item : choose_clinic_btn.getItems()) //finding the clinic that we chose
        {
            item.setOnAction(actionEvent -> {
                for(ClinicEntity clinic : SimpleClient.getClinicList()) {
                    if (clinic.getName().equals(item.getText())) {
                        chosen_clinic = clinic;
                        chosen_clinic.setManager(clinic.getManager());
//                        if(!chosen_clinic.getManager().equals(null)) {
//                            list_view.getItems().add(new Button(chosen_clinic.getManager().getFirst_name() + "-" + chosen_clinic.getManager().getId()));
//                        }
                        for (ManagerEntity manager : SimpleClient.Managers) //finding the clinic manager
                        {
                            if (manager.getClinic().getName().equals(chosen_clinic.getName())) {
                                //chosen_clinic.setManager(manager);
                                list_view.getItems().add(new Button(chosen_clinic.getManager().getFirst_name() + "-" + chosen_clinic.getManager().getId()));
                            }
                        }
                        for(PatientEntity patient : SimpleClient.Patients)
                        {
                            if(!list_view.getItems().contains(patient.getFirst_name()+ "-" + patient.getId())) {
                                list_view.getItems().add(new Button(patient.getFirst_name() + "-" + patient.getId()));
                            }
                        }
                        for(Button client : list_view.getItems())
                        {
                            client.setOnAction(ActionEvent -> {
                                System.out.println("manager= "+chosen_clinic.getManager().getFirst_name() + "-" + chosen_clinic.getManager().getId());
                                if((chosen_clinic.getManager().getFirst_name() + "-" + chosen_clinic.getManager().getId()).equals(client.getText()))
                                {
                                    chosen_manager=chosen_clinic.getManager();
                                    System.out.println("aaaaa");
                                    try {
                                        App.setRoot("magneticCardManager");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    for(PatientEntity patient : SimpleClient.Patients )
                                    {
                                        if((patient.getFirst_name()+ "-" + patient.getId()).equals(client.getText()))
                                        {
                                            chosen_patient = patient;
                                        }
                                    }
                                    ID.setText(Integer.toString(chosen_patient.getId()));
                                    Password.setText("*********");
                                    try {
                                        App.setRoot("magnetic_card");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    }
                }
            });
        }

    }

}
