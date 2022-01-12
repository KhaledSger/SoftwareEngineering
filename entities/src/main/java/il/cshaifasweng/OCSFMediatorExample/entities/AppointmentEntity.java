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
    private String time; // time of the appointment   must be initialized by generated value of 15 minutes each between open and close hours of clinic
    private String actual_date; // time of treatment
    private boolean reserved = false; // the appointment is not reserved by default
    private String duration="0";
    @ManyToOne
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;

    @ManyToOne
    @JoinColumn(name = "Patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "Doctor_id")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "Nurse_id")
    private NurseEntity Nurse;

    public AppointmentEntity() {

    }

    public AppointmentEntity(String actual_date, String date,String time, ClinicEntity clinic,PatientEntity patient,DoctorEntity doctor,NurseEntity nurse,boolean valid,String duration)
    {
        this.date=date;
        this.time=time;
        setClinic_app(clinic);
        setPatient_app(patient);
        setDoctor_app(doctor);
        setNurse_app(nurse);
        this.actual_date=actual_date;
        this.reserved = valid;
        this.duration = duration;
    }

    public void setClinic_app(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getAppointments().add(this);
    }

    public void setPatient_app(PatientEntity patient) {
        this.patient=patient;
        patient.getAppointments().add(this);
    }

    public void setDoctor_app(DoctorEntity doctor) {
        this.doctor=doctor;
        doctor.getAppointments().add(this);
    }

    public void setNurse_app(NurseEntity Nurse) {
        this.Nurse=Nurse;
        Nurse.getAppointments().add(this);
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

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public NurseEntity getNurse() {
        return Nurse;
    }

    public void setNurse(NurseEntity nurse) {
        Nurse = nurse;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
