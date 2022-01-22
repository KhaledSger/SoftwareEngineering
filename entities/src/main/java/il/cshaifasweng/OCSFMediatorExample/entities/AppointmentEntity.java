package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Appointments")
public class AppointmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "Appointment_id")
    private int id;
    private LocalDateTime date; // date of the appointment
    private LocalDateTime actual_date; // time of treatment
    private boolean reserved = false; // the appointment is not reserved by default
    private int duration;

    @ManyToOne
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;

    @ManyToOne
    @JoinColumn(name = "Patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "Doctor_id")
    private DoctorEntity doctor;

//    @ManyToOne
//    @JoinColumn(name = "Nurse_id")
//    private NurseEntity Nurse;

    public AppointmentEntity() {

    }

    public AppointmentEntity(LocalDateTime actual_date, LocalDateTime date, ClinicEntity clinic,PatientEntity patient,DoctorEntity doctor,boolean valid,int duration)
    {
        this.date=date;
        this.clinic = clinic;
        this.patient = patient;
        this.doctor = doctor;
        this.actual_date=actual_date;
        this.reserved = valid;
        this.duration = duration;
    }
    public AppointmentEntity(LocalDateTime date, DoctorClinicEntity doc_clinic,int duration)
    {
        this.date=date;
        this.clinic = doc_clinic.clinic;
        this.doctor = doc_clinic.doctor;
        this.reserved = false;
        this.duration = duration;
    }
    public AppointmentEntity(LocalDateTime actual_date,LocalDateTime date, ClinicEntity clinic,PatientEntity patient,NurseEntity nurse,boolean valid,int duration)
    {
        this.date=date;
        this.clinic = clinic;
        this.patient = patient;
//        setNurse_app(nurse);
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

//    public void setNurse_app(NurseEntity Nurse) {
//        this.Nurse=Nurse;
//        Nurse.getAppointments().add(this);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public LocalDateTime getActual_date() {
        return actual_date;
    }

    public void setActual_date(LocalDateTime actual_date) {
        this.actual_date = actual_date;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;

    }

//    public NurseEntity getNurse() {
//        return Nurse;
//    }

//    public void setNurse(NurseEntity nurse) {
//        Nurse = nurse;
//    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}