package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor_entity")
public class DoctorEntity extends EmployeeEntity {
    public DoctorEntity(int id, String employee_name, String family_name, String mail, String specialization) {
        super(id, employee_name, family_name, mail);
        Specialization = specialization;
        this.doctorClinicEntities=new ArrayList<DoctorClinicEntity>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctor_id;
    String Specialization;

    @OneToMany(mappedBy = "doctor")
    List<DoctorClinicEntity> doctorClinicEntities;

    // TODO Add Many-to-many relation with clinic
    //TODO DataBase Connection
    public DoctorEntity() {
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
}
