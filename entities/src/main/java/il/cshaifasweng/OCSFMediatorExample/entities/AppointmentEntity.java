package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Appointments")
public class AppointmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "Appointment_id")
    private int id;
    private String date; // date of the appointment
    private String time; // time of the appointment
    private String actual_date; // time of treatment
    @ManyToOne
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;

    @ManyToOne
    @JoinColumn(name = "Patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "Employee_id")
    private EmployeeEntity employee;

    public AppointmentEntity() {

    }

    public AppointmentEntity(String actual_date, String date,String time, ClinicEntity clinic,PatientEntity patient,EmployeeEntity employee)
    {
        this.date=date;
        this.time=time;
        setClinic_app(clinic);
        setPatient_app(patient);
        setEmployee_app(employee);
        this.actual_date=actual_date;
    }

    public void setClinic_app(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getAppointments().add(this);
    }

    public void setPatient_app(PatientEntity patient) {
        this.patient=patient;
        clinic.getAppointments().add(this);
    }

    public void setEmployee_app(EmployeeEntity employee) {
        this.employee=employee;
        clinic.getAppointments().add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ClinicEntity getClinic() {
        return clinic;
    }

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public String getActual_date() {
        return actual_date;
    }

    public void setActual_date(String actual_date) {
        this.actual_date = actual_date;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
