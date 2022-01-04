package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Clinic")
public class ClinicEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Clinic_id")
    private int id;
    private String name;
    private String open;
    private String close;
    private String[] services;


    @OneToMany
    @JoinColumn(name = "Clinic_id")
    private List<PatientEntity> Patients;

    @OneToMany
    @JoinColumn(name = "Clinic_id")
    private List<NurseEntity> nurseEntities;




    public ClinicEntity(String name, String open, String close, String[] services,List<PatientEntity> Patients) {
        this.name = name;
        this.open = open;
        this.close = close;
        this.services = services;

        this.Patients=new ArrayList<PatientEntity>();
        this.nurseEntities=new ArrayList<NurseEntity>();

    }


    public ClinicEntity(ClinicEntity CE) {
        this.name = CE.name;
        this.open = CE.open;
        this.close = CE.close;
        this.services = CE.services;
        this.Patients=CE.Patients;
        this.nurseEntities= CE.nurseEntities;
    }

    public ClinicEntity() {
    }

    public ClinicEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public String[] getServices() {
        return services;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public int getId() {
        return id;
    }


    public List<PatientEntity> getPatients() {
        return Patients;
    }
    public List<NurseEntity> getNurseEntities() {
        return nurseEntities;
    }
}