package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "manager_entity")

public class ManagerEntity extends UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private int manager_id;

    @OneToOne
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;


    public ManagerEntity(int id, String first_name, String family_name, String mail,String Password ,ClinicEntity clinic) throws NoSuchAlgorithmException {
        super(id, first_name, family_name, mail,Password);
        setClinic(clinic);
    }

    public ManagerEntity() {
    }


    public int getManager_id() {
        return manager_id;
    }



    public ClinicEntity getClinic() {
        return clinic;
    }

    public void setClinic(ClinicEntity clinic1) {
        this.clinic = clinic1;
        clinic1.updateManager(this);
    }
}

