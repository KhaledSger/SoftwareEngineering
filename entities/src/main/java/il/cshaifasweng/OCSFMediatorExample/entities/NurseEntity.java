package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Nurse")
public class NurseEntity extends UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Nurse_id")
    private int nurse_id;


     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="Clinic_id")
     private ClinicEntity clinic;

    @OneToMany
    @JoinColumn(name = "Appointment_id")
    private List<AppointmentEntity> appointments;


    public NurseEntity(int id, String employee_name, String family_name, String mail,String Password,ClinicEntity clinic) throws NoSuchAlgorithmException {
        super(id, employee_name, family_name, mail,Password);
        setClinic(clinic);
        this.appointments=new ArrayList<AppointmentEntity>();
    }

    public int getNurse_id() {
        return nurse_id;
    }



    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getNurseEntities().add(this);
    }

    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }
}
