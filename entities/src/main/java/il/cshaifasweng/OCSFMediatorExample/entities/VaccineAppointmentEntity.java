package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "VaccineAppointments")
public class VaccineAppointmentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vac_id")
    private int id;
    private LocalDateTime date; // date of the appointment
    private LocalDateTime actual_date; // time of treatment
    private boolean reserved = false; // the appointment is not reserved by default
    private int duration;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Patient_id")
    private PatientEntity patient;

    public VaccineAppointmentEntity(LocalDateTime date, int duration, ClinicEntity clinic) {
        this.id = id;
        this.date = date;
        this.reserved = false;
        this.duration = duration;
        setClinic(clinic);
        this.patient = null;
    }

    public VaccineAppointmentEntity() {

    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getActual_date() {
        return actual_date;
    }

    public boolean isReserved() {
        return reserved;
    }

    public int getDuration() {
        return duration;
    }

    public ClinicEntity getClinic() {
        return clinic;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setActual_date(LocalDateTime actual_date) {
        this.actual_date = actual_date;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getVac_appointments().add(this);
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
        patient.getVac_appointments().add(this);
    }
}