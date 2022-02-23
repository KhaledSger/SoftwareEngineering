package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PatientClient  extends AbstractClient {
    private static PatientClient client = null;
    public static PatientEntity Patient = null;
    public int appointment_flag= -1 ;
    public int cancel_app_flag=0;

    public PatientClient(String host, int port,PatientEntity patient) {
        super(host, port);
       this.Patient=patient;
    }
    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg.getClass().equals(Warning.class)) {
            EventBus.getDefault().post(new WarningEvent((Warning) msg));
        }
        else if (msg.getClass().equals(PatientEntity.class)){
           Patient = (PatientEntity) msg;
        }
        else if(msg.getClass().equals(String.class))
        {
            if(((String)msg).equals("reservation done!"))
            {
                appointment_flag=1;
            }
            else if(((String)msg).equals("failed to reserve the appointment!"))
            {
                appointment_flag=0;
            }
            else if(((String)msg).equals("Appointment Cancelled!")){
                cancel_app_flag=1;
            }
        }
        else if(msg.getClass().equals(ArrayList.class))
        {
            this.Patient.setAppointments((ArrayList)msg);
            System.out.println("patient app size= "+this.Patient.getAppointments().size());
        }
        else if(msg.toString().startsWith("patient vac:"))
        {
           String msgString= msg.toString().substring(12);
           this.Patient.setVac_appointments((ArrayList<VaccineAppointmentEntity>) msg);
        }
    }

    public static PatientClient getClient() {
        if (client == null) {
            client = new PatientClient("localhost", 3000,Patient);
        }
        return client;
    }
    public List<DoctorPatientEntity> getDoctorPatientEntities()
    {
        return Patient.getDoctorPatientEntities();
    }

    public ClinicEntity getClinic()
    {
        return this.Patient.getClinic();
    }

    public String getName()
    {
        return (this.Patient.getFirst_name() + " " + this.Patient.getFamily_name());
    }

    public int getAge()
    {
        return this.Patient.getAge();
    }

    public int getId()
    {
        return this.Patient.getId();
    }

    public List<AppointmentEntity> getAppointments()
    {
        return Patient.getAppointments();
    }

    public PatientEntity getPatient()
    {
        return  this.Patient;
    }

}
