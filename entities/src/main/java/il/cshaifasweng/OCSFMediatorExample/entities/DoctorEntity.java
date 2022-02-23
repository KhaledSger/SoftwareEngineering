package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctor_entity")

public class DoctorEntity extends UserEntity {
    public DoctorEntity(int id, String first_name, String family_name, String mail,String Password,String specialization) throws NoSuchAlgorithmException {
        super(id, first_name, family_name, mail,Password);
        this.Specialization = specialization;
        initListsAndSets();

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctor_id;
    String Specialization;

    @OneToMany(mappedBy = "doctor")
    List<DoctorClinicEntity> doctorClinicEntities;

    @OneToMany(mappedBy = "doctor")
    List<DoctorPatientEntity> doctorPatientEntities;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<AppointmentEntity> appointments;

    private void initListsAndSets() {
        this.doctorClinicEntities=new ArrayList<DoctorClinicEntity>();
        this.doctorPatientEntities=new ArrayList<DoctorPatientEntity>();
        this.appointments=new ArrayList<AppointmentEntity>();
    }

    // TODO Add Many-to-many relation with clinic
    //TODO DataBase Connection
    public DoctorEntity() {
        initListsAndSets();
    }


    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }


    public int getDoctor_id() {
        return doctor_id;
    }

    public List<DoctorClinicEntity> getDoctorClinicEntities() {
        return doctorClinicEntities;
    }

    public void setDoctorClinicEntities(List<DoctorClinicEntity> doctorClinicEntities) {
        this.doctorClinicEntities = doctorClinicEntities;
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

    public List<AppointmentEntity> getResevedAppointments() {
        List<AppointmentEntity> reserved_Apps = new ArrayList<AppointmentEntity>();
        for(AppointmentEntity app : this.appointments)
        {
            if(app.isReserved() && app.getPatient() != null)
            {
                reserved_Apps.add(app);
            }
        }
        return reserved_Apps;
    }
}
