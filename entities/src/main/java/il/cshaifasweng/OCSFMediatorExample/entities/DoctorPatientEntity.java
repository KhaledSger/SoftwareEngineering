package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DoctorPatientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DoctorPatient_id")
    private int DoctorPatient_id;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name="Patient_id")
    PatientEntity patient;


    public DoctorPatientEntity(DoctorEntity doctor, PatientEntity patient) {
        setDoctor(doctor);
        setPatient(patient);
    }
    public DoctorPatientEntity() {
    }

    public int getDoctorPatient_id() {
        return DoctorPatient_id;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
        doctor.getDoctorPatientEntities().add(this);

    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
        this.patient.getDoctorPatientEntities().add(this);
    }


}
