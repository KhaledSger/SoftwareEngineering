package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
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
    private int id;
    private String name;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;

    @OneToMany
    @JoinColumn(name = "Appointment_id")
    private List<AppointmentEntity> appointments;



    @OneToMany(mappedBy = "patient")
    private List<DoctorPatientEntity> doctorPatientEntities;


    public PatientEntity(int id, String first_name,String family_name,String mail ,String Password,int age,ClinicEntity clinic) throws NoSuchAlgorithmException {
        super(id, first_name, family_name, mail,Password);
        this.age = age;
        this.appointments = new ArrayList<AppointmentEntity>();
        setClinic(clinic);
        this.doctorPatientEntities=new ArrayList<DoctorPatientEntity>();
    }

    public PatientEntity(){

    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }



    public void setName(String name) {
        this.name = name;
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

    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    public List<DoctorPatientEntity> getDoctorPatientEntities() {
        return doctorPatientEntities;
    }

    public void setDoctorPatientEntities(List<DoctorPatientEntity> doctorPatientEntities) {
        this.doctorPatientEntities = doctorPatientEntities;
    }
}
