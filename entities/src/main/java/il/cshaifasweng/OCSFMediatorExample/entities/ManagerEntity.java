package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;

@Entity
@Table(name = "manager_entity")

public class ManagerEntity extends EmployeeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private int manager_id;

    @OneToOne
    @JoinColumn(name = "Clinic_id")
    private ClinicEntity clinic;


    public ManagerEntity(int id, String employee_name, String family_name, String mail, ClinicEntity clinic) {
        super(id, employee_name, family_name, mail);
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

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
    }
}

