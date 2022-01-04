package il.cshaifasweng.OCSFMediatorExample.entities;





import javax.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class DoctorClinicEntity {
    public DoctorClinicEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DoctorClinic_id")
    private int DoctorClinic_id;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    DoctorEntity doctor;
    
    @ManyToOne
    @JoinColumn(name="clinic_id")
    ClinicEntity clinic;

   private Map<Integer,String> day_hours;


    public DoctorClinicEntity(DoctorEntity doctor, ClinicEntity clinic, Map<Integer, String> day_hours) {
        this.doctor = doctor;
        this.clinic = clinic;
        this.day_hours = day_hours ;

    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
        doctor.getDoctorClinicEntities().add(this);
    }

    public ClinicEntity getClinic() {
        return clinic;
    }

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getDoctorClinicEntities().add(this);
    }

    public Map<Integer, String> getDay_hours() {
        return day_hours;
    }

    public void setDay_hours(Map<Integer, String> day_hours) {
        this.day_hours = day_hours;
    }

    public int getDoctorClinic_id() {
        return DoctorClinic_id;
    }
}
