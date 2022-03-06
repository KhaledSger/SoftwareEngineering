/**
 * Sample Skeleton for 'covidTestApps.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.AppointmentEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.VaccineAppointmentEntity;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CovidTestAppsController {

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

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="datePickerBtn"
    private DatePicker datePickerBtn; // Value injected by FXMLLoader

    @FXML // fx:id="list_view"
    private ListView<Button> list_view; // Value injected by FXMLLoader


    @FXML // fx:id="welcome_text"
    private Text welcome_text; // Value injected by FXMLLoader

    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
        App.setRoot("covidTest");
    }

    @FXML
    void datePickerAction(ActionEvent event) {
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'covidTestApps.fxml'.";
        assert datePickerBtn != null : "fx:id=\"datePickerBtn\" was not injected: check your FXML file 'covidTestApps.fxml'.";
        assert list_view != null : "fx:id=\"list_view\" was not injected: check your FXML file 'covidTestApps.fxml'.";
        assert welcome_text != null : "fx:id=\"welcome_text\" was not injected: check your FXML file 'covidTestApps.fxml'.";
        welcome_text.setText(SimpleClient.patientClient.getName());
        for(VaccineAppointmentEntity app : SimpleClient.getPatientClient().getClinic().getVac_appointments())
        {
            if(!app.isReserved() && app.getType().equals("covid test"))  // display the covid-19 tests appointments that are not reserved
            {
                Button btn = new Button(app.getDate().toString());
                list_view.getItems().add(btn);
                btn.setOnAction(ActionEvent -> {  // setting an action for each button that we add
                    try {
                        SimpleClient.getClient().sendToServer(app);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                String.format("Please confirm your reservation!")
                        );
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            app.setPatient(SimpleClient.patientClient.getPatient());
                            Platform.runLater(() -> {
                                Alert alert1 = new Alert(Alert.AlertType.INFORMATION,
                                        String.format("choose info receiving method:"), new ButtonType("email"), new ButtonType("phone")
                                );
                                Optional<ButtonType> method_result = alert1.showAndWait();
                                try {
                                    app.setReserved(true);
                                    SimpleClient.getClient().sendToServer(app);
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
                            app.setReserved(false);
                            try {
                                SimpleClient.getClient().sendToServer(app);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                });
            }

        }
    }

}
