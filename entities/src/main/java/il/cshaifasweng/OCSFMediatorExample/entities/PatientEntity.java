package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Patient")
public class PatientEntity extends UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Patient_id")
    private int patient_id;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;



    @OneToMany(mappedBy = "patient")
    List<DoctorPatientEntity> doctorPatientEntities;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "patient")
    private List<AppointmentEntity> appointments;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "patient")
    private List<VaccineAppointmentEntity> vac_appointments;

    public PatientEntity(int id, String first_name,String family_name,String mail ,String Password,int age,ClinicEntity clinic) throws NoSuchAlgorithmException {
        super(id, first_name, family_name, mail,Password);
        this.age = age;
        this.clinic = clinic; // TODO change tp set
        this.doctorPatientEntities=new ArrayList<DoctorPatientEntity>();
        this.appointments=new ArrayList<AppointmentEntity>();
        this.vac_appointments = new ArrayList<VaccineAppointmentEntity>();
    }

    public PatientEntity() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getPatients().add(this);
    }

    public ClinicEntity getClinic() {
        return clinic;
    }

    public List<DoctorPatientEntity> getDoctorPatientEntities() {
        return doctorPatientEntities;
    }

    public void setDoctorPatientEntities(List<DoctorPatientEntity> doctorPatientEntities) {
        this.doctorPatientEntities = doctorPatientEntities;
    }
    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    public int getPatientId()
    {
        return this.patient_id;
    }

    public List<VaccineAppointmentEntity> getVac_appointments() {
        return vac_appointments;
    }

    public void setVac_appointments(List<VaccineAppointmentEntity> vac_appointments) {
        this.vac_appointments = vac_appointments;
    }
}
