
package il.cshaifasweng.OCSFMediatorExample.client;

        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.MenuButton;

public class PatientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button CovidTestBtn;

    @FXML
    private Button CovidVaccineBtn;

    @FXML
    private Button FamilyDocButton;

    @FXML
    private Button GreenPassBtn;

    @FXML
    private Button LabTest;

    @FXML
    private Button NurseAppBtn;

    @FXML
    private MenuButton SpecializedDoctorMenuBtn;

    @FXML
    void CovidTestAction(ActionEvent event) {

    }

    @FXML
    void CovidVaccineAction(ActionEvent event) {

    }

    @FXML
    void FamilyDocAction(ActionEvent event) {

    }

    @FXML
    void GreenPassAction(ActionEvent event) {

    }

    @FXML
    void LabTestAction(ActionEvent event) {

    }

    @FXML
    void NurseAppAction(ActionEvent event) {

    }

    @FXML
    void SpecializedDoctorAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert CovidTestBtn != null : "fx:id=\"CovidTestBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert CovidVaccineBtn != null : "fx:id=\"CovidVaccineBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert FamilyDocButton != null : "fx:id=\"FamilyDocButton\" was not injected: check your FXML file 'patient.fxml'.";
        assert GreenPassBtn != null : "fx:id=\"GreenPassBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert LabTest != null : "fx:id=\"LabTest\" was not injected: check your FXML file 'patient.fxml'.";
        assert NurseAppBtn != null : "fx:id=\"NurseAppBtn\" was not injected: check your FXML file 'patient.fxml'.";
        assert SpecializedDoctorMenuBtn != null : "fx:id=\"SpecializedDoctorMenuBtn\" was not injected: check your FXML file 'patient.fxml'.";

    }

}
