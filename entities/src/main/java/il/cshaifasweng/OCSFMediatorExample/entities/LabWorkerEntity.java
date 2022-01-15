package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Entity
@Table(name="LabWorker")
public class LabWorkerEntity extends UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LabWorker_id")
    private int worker_id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Clinic_id")
    private ClinicEntity clinic;

    public LabWorkerEntity(int id, String employee_name, String family_name, String mail,String Password,ClinicEntity clinic) throws NoSuchAlgorithmException {
        super(id, employee_name, family_name, mail, Password);
        setClinic(clinic);
    }

    public LabWorkerEntity() {

    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public ClinicEntity getClinic() {
        return clinic;
    }

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getLabWorkers().add(this);
    }

}
