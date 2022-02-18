/**
 * Sample Skeleton for 'patient.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import com.sun.jdi.connect.spi.TransportService;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class PatientController {

    // set patients name in initialize()
    /* add buttons for appointments dynamically like this:
        ToggleGroup group = new ToggleGroup();
        ToggleButton btn1 = new ToggleButton("aaa");
		ToggleButton btn2 = new ToggleButton("bbb");
		vBox.getChildren().add(btn1);
		vBox.getChildren().add(btn2);
		group.getToggles().add(btn1);
	    group.getToggles().add(btn2); we add the buttons to the same toggle group so we can press only one button at a time
		this way you add two toggle buttons with the text "aaa" in the first one and "bbb" in the second one
    */
    /*
        add menu items to specialized doctor menu dynamically like this:
        // SpecializedDoctorMenuBtn.getItems().add(new MenuItem("specialization"));
    */

    public String specialation_of_doctor;
    public String chosen_doctor_name;
    public String[] chosen_doctor_array;
    private DoctorEntity chosen_doctor;
    public List<DoctorClinicEntity> doc_clinic_list;
    public List<AppointmentEntity> appointments = SimpleClient.patientClient.getAppointments();
    public Set<AppointmentEntity> doctor_appointments;
    private AppointmentEntity chosen_appointment;
    private DoctorEntity chosen_family_doctor;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="CovidTestBtn"
    private ToggleButton CovidTestBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Flu_vaccine_btn"
    private MenuItem Flu_vaccine_btn; // Value injected by FXMLLoader

    @FXML // fx:id="GreenPassBtn"
    private Button GreenPassBtn; // Value injected by FXMLLoader

    @FXML // fx:id="LabTest"
    private ToggleButton LabTest; // Value injected by FXMLLoader

    @FXML // fx:id="NurseAppBtn"
    private ToggleButton NurseAppBtn; // Value injected by FXMLLoader

    @FXML // fx:id="SpecializedDoctorMenuBtn"
    private MenuButton SpecializedDoctorMenuBtn; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="covid_vaccine_btn"
    private MenuItem covid_vaccine_btn; // Value injected by FXMLLoader

    @FXML // fx:id="datePickerBtn"
    private DatePicker datePickerBtn; // Value injected by FXMLLoader

    @FXML // fx:id="familyDocBtn"
    private ToggleButton familyDocBtn; // Value injected by FXMLLoader

    @FXML // fx:id="vaccine_btn"
    private MenuButton vaccine_btn; // Value injected by FXMLLoader

    @FXML // fx:id="viewAppsBtn"
    private ToggleButton viewAppsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    private ListView<Button> vBox;

    @FXML
    void CovidTestAction(ActionEvent event) throws IOException {
        App.setRoot("covidTest");

    }

    @FXML
    void FamilyDocAction(ActionEvent event) throws InterruptedException
    {
        String specialization ;
        if(SimpleClient.patientClient.getAge() >= 18) // decide which doctor needs to treat the patient according to his age
            specialization = "Family Doctor";
        else
            specialization = "Children Doctor";
        for (DoctorClinicEntity doc_patient : SimpleClient.patientClient.getClinic().getDoctorClinicEntities()) { // we search for the family doctor or children doctor in the clinic of the patient
            if (doc_patient.getDoctor().getSpecialization().equals(specialization)) {
                chosen_family_doctor = doc_patient.getDoctor();
                for (AppointmentEntity app : doc_patient.getDoctor().getAppointments()) {
                    if (!(app.getDate().isAfter(LocalDateTime.now().plusWeeks(4)))) // adding the appointments fot the next four weeks
                        vBox.getItems().add(new Button(app.getDate().toString()));
                    vBox.getItems().sort(Comparator.comparing(o -> o.getText()));
                }
            }
        }
            for (Button button : vBox.getItems()) {
                button.setOnAction(ActionEvent ->
                {
                    for (AppointmentEntity app1 : chosen_family_doctor.getAppointments()) {
                        if (app1.getDate().toString().equals(button.getText())) {
                            app1.setReserved(true);
                            //send to server
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                        String.format("Please confirm your reservation!")
                                );
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    app1.setPatient(SimpleClient.patientClient.getPatient());
                                    Platform.runLater(() -> {
                                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION,
                                                String.format("choose info receiving method:"), new ButtonType("email"), new ButtonType("phone")
                                        );
                                        Optional<ButtonType> method_result = alert1.showAndWait();
                                        try {
                                            SimpleClient.getClient().sendToServer(app1);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    });

                                } else if (result.get() == ButtonType.CANCEL) {
                                    app1.setReserved(false);
                                }
                            });
                        }
                    }
                });
            }

    }

    @FXML
    void Flu_vaccine_action(ActionEvent event) {
        datePickerBtn.show();
    }

    @FXML
    void GreenPassAction(ActionEvent event) throws IOException {
        /* check if the patients is vaccinated for covid
        if not return an error message
        if yes check if the vaccination is still valid by checking the date of the vaccination
        if its not valid return an error message
        if yes move to the green pass page  ----> App.setRoot("GreenPass");
         */
        App.setRoot("GreenPass");
    }

    @FXML
    void LabTestAction(ActionEvent event) {
        datePickerBtn.show();
    }

    @FXML
    void NurseAppAction(ActionEvent event) {
        datePickerBtn.show();
    }

    @FXML
    void SpecializedDoctorAction(ActionEvent event) {

    }

    @FXML
    void backBtnAction(ActionEvent event) throws IOException { // go back to the previous page
        App.setRoot("primary");
        SimpleClient.resetClient();
    }

    @FXML
    void covid_vaccine_action(ActionEvent event) {
        datePickerBtn.show();
    }

    @FXML
    void datePickerAction(ActionEvent event) {

    }

    @FXML
    void vaccineAction(ActionEvent event) {
        datePickerBtn.show();
    }

    @FXML
    void viewAppsAction(ActionEvent event) { // view my appointments
        for(AppointmentEntity app : SimpleClient.patientClient.getAppointments())
        {
            vBox.getItems().clear();

        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize()
    {
        assert CovidTestBtn != null : "fx:id=\"CovidTestBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert Flu_vaccine_btn != null : "fx:id=\"Flu_vaccine_btn\" was not injected: check your FXML file 'patient.fxml'.";
        assert GreenPassBtn != null : "fx:id=\"GreenPassBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert LabTest != null : "fx:id=\"LabTest\" was not injected: check your FXML file 'patient.fxml'.";
        assert NurseAppBtn != null : "fx:id=\"NurseAppBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert SpecializedDoctorMenuBtn != null : "fx:id=\"SpecializedDoctorMenuBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert covid_vaccine_btn != null : "fx:id=\"covid_vaccine_btn\" was not injected: check your FXML file 'patient.fxml'.";
        assert datePickerBtn != null : "fx:id=\"datePickerBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert familyDocBtn != null : "fx:id=\"familyDocBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert vaccine_btn != null : "fx:id=\"vaccine_btn\" was not injected: check your FXML file 'patient.fxml'.";
        assert viewAppsBtn != null : "fx:id=\"viewAppsBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'patient.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'patient.fxml'.";
        welcome_text.setText(SimpleClient.patientClient.getName());  // add patient's name here
        for (ClinicEntity clinic : SimpleClient.getClinicList()) { // adding the specialization of the doctors
            for (DoctorClinicEntity doc_clinic : clinic.getDoctorClinicEntities()) {
                if ((!SpecializedDoctorMenuBtn.getItems().contains(doc_clinic.getDoctor().getSpecialization())) && !doc_clinic.getDoctor().getSpecialization().equals("Family Doctor") && !doc_clinic.getDoctor().getSpecialization().equals("Children Doctor"))
                {
                    SpecializedDoctorMenuBtn.getItems().add(new MenuItem(doc_clinic.getDoctor().getSpecialization()));
                }

            }
        }
        for (MenuItem item : SpecializedDoctorMenuBtn.getItems())//setting action events for each item in specialized doctor menu
        {
            item.setOnAction(actionEvent -> {
                        vBox.getItems().clear();
                        doc_clinic_list = new ArrayList<DoctorClinicEntity>();
                        //datePickerBtn.show();
                        System.out.println(" here print list doctor entity !!");
                        //System.out.println(item.getText());
                        specialation_of_doctor = item.getText();
                        //// find doctor name and doctor clinic by specialization
                        for (ClinicEntity clinic : SimpleClient.getClinicList()) {
                            for (DoctorClinicEntity doc_clinic : clinic.getDoctorClinicEntities()) {
                                if (doc_clinic.getDoctor().getSpecialization() == specialation_of_doctor) {
                                    doc_clinic_list.add(doc_clinic);

                                }

                            }
                            appointments.sort(Comparator.comparing(o -> o.getDate())); // sorting the appointment list by date
                            for (AppointmentEntity app : appointments) {
                                if (app.getDoctor().getSpecialization().equals(specialation_of_doctor)) {
                                    vBox.getItems().add(new Button(app.getDoctor().getFirst_name() + " " + app.getDoctor().getFamily_name() + "-" + app.getClinic().getName()));
                                }

                            }
                            for (DoctorClinicEntity doc_clinic_entity : doc_clinic_list) {
                                if (doc_clinic_entity.getDoctor().getSpecialization().equals(specialation_of_doctor)) {
                                    if (!(vBox.getItems().contains(doc_clinic_entity.getDoctor().getFirst_name() + " " + doc_clinic_entity.getDoctor().getFamily_name() + "-" + doc_clinic_entity.getClinic().getName()))) {
                                        vBox.getItems().add(new Button(doc_clinic_entity.getDoctor().getFirst_name() + " " + doc_clinic_entity.getDoctor().getFamily_name() + "-" + doc_clinic_entity.getClinic().getName()));
                                    }
                                }
                            }

                            for (Button bt : vBox.getItems())//setting action events for each button in vbox
                            {
                                bt.setOnAction(actionEvent1 ->
                                        {
                                            chosen_doctor_name = bt.getText();
                                            chosen_doctor_array = chosen_doctor_name.split("-");
                                            for (ClinicEntity clinic1 : SimpleClient.getClinicList()) {
                                                for (DoctorClinicEntity doc_clinic1 : clinic1.getDoctorClinicEntities()) {
                                                    if (chosen_doctor_array[0].equals(doc_clinic1.getDoctor().getFirst_name() + " " + doc_clinic1.getDoctor().getFamily_name())) {
                                                        chosen_doctor = doc_clinic1.getDoctor();
                                                        System.out.println("hello world");
                                                        vBox.getItems().clear(); //deleting the list items so we can add the appointments instead
                                                        //datePickerBtn.show();  // need to change the free appointments with datePickerBtn


                                                        for (AppointmentEntity app : chosen_doctor.getAppointments()) {
                                                            if (app.isReserved() == false /*&& app.getClinic().getName().equals(chosen_doctor_array[1])*/) {
                                                                vBox.getItems().add(new Button(app.getDate().toString()));
                                                                vBox.getItems().sort(Comparator.comparing(o -> o.getText()));
                                                            }
                                                        }
                                                    }

                                                }
                                            }

                                            for (Button button : vBox.getItems()) {
                                                button.setOnAction(ActionEvent ->
                                                {
                                                    for (AppointmentEntity app1 : chosen_doctor.getAppointments()) {
                                                        if (app1.getDate().toString().equals(button.getText())) {
                                                            try {
                                                                SimpleClient.getClient().sendToServer(app1); // sending to server so that it updates the Reserved field to true
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Platform.runLater(() -> {
                                                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                                                        String.format("Please confirm your reservation!")
                                                                );
                                                                Optional<ButtonType> result = alert.showAndWait();
                                                                if (result.get() == ButtonType.OK) {
                                                                    app1.setPatient(SimpleClient.patientClient.getPatient());
                                                                    Platform.runLater(() -> {
                                                                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION,
                                                                                String.format("choose info receiving method:"), new ButtonType("email"), new ButtonType("phone")
                                                                        );
                                                                        Optional<ButtonType> method_result = alert1.showAndWait();
                                                                        try {
                                                                            app1.setReserved(true);
                                                                            SimpleClient.getClient().sendToServer(app1);
                                                                            while(SimpleClient.patientClient.appointment_flag ==-1)
                                                                            {
                                                                               ProgressBar pb = new ProgressBar(0.6);
                                                                               ProgressBar pi = new ProgressBar(0.6);
                                                                            }
                                                                            if(SimpleClient.patientClient.appointment_flag ==1)
                                                                            {
                                                                                Platform.runLater(() -> {
                                                                                    Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION,
                                                                                            String.format("reserved successfully!")
                                                                                    );
                                                                                    alert3.show();
                                                                                });
                                                                            }
                                                                            else
                                                                            {
                                                                                Platform.runLater(() -> {
                                                                                    Alert alert4 = new Alert(Alert.AlertType.ERROR,
                                                                                            String.format("failed to reserve the appointment!")
                                                                                    );
                                                                                    alert4.show();
                                                                                });
                                                                            }
                                                                            SimpleClient.patientClient.appointment_flag=-1;
                                                                        } catch (IOException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    });
                                                                }
                                                                else if (result.get() == ButtonType.CANCEL) {
                                                                    app1.setReserved(false);
                                                                    try {
                                                                        SimpleClient.getClient().sendToServer(app1);
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }

                                        }
                                );

                            }
                        }
                    }
            );
        }

    }
}
