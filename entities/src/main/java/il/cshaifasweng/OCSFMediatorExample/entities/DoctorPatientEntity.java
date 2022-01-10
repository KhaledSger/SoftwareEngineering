package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
@Entity
public class DoctorPatientEntity {
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
        this.doctor = doctor;
        this.patient = patient;
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
        patient.getDoctorPatientEntities().add(this);
    }


}
