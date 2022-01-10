package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "Nurse")
public class NurseEntity extends UserEntity{
    public NurseEntity(int id, String employee_name, String family_name, String mail,String Password,ClinicEntity clinic) throws NoSuchAlgorithmException {
        super(id, employee_name, family_name, mail,Password);
        this.clinic = clinic;
    }

     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="Clinic_id")
     private ClinicEntity clinic;

    public int getNurse_id() {
        return nurse_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Nurse_id")
    private int nurse_id;

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getNurseEntities().add(this);
    }
}
