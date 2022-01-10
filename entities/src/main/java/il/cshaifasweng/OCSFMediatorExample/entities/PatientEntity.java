package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Patient")
public class PatientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Patient_id")
    private int patient_id;
    private int id;
    private String name;
    private String password;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;

    @OneToMany
    @JoinColumn(name = "Appointment_id")
    private List<AppointmentEntity> appointments;


    public PatientEntity(int id, String name, String password, int age,ClinicEntity clinic) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.appointments = new ArrayList<AppointmentEntity>();
        setClinic(clinic);
    }

    public PatientEntity() {

    }


    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
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
}
