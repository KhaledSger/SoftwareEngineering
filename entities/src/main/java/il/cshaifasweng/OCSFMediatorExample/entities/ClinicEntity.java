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
    private String name; // name of the clinic
    private String open; //clinic opening hour
    private String close; // clinic closing hour
    private String[] services; // the services that the clinic provides


    @OneToMany
    @JoinColumn(name = "Patient_id")
    private List<PatientEntity> Patients;

    @OneToMany
    @JoinColumn(name = "Nurse_id")
    private List<NurseEntity> nurseEntities;

    @OneToMany(mappedBy = "clinic")
    private  List<DoctorClinicEntity> doctorClinicEntities;

    @OneToMany
    @JoinColumn(name = "Appointment_id")
    private List<AppointmentEntity> appointments;



    public ClinicEntity(String name, String open, String close, String[] services) {
        this.name = name;
        this.open = open;
        this.close = close;
        this.services = services;

        this.Patients=new ArrayList<PatientEntity>();
        this.nurseEntities=new ArrayList<NurseEntity>();
        this.doctorClinicEntities=new ArrayList<DoctorClinicEntity>();
        this.appointments = new ArrayList<AppointmentEntity>();
    }

    public ClinicEntity(String name, String open, String close, String[] services,List<PatientEntity> Patients,List<AppointmentEntity> apps,List<NurseEntity> nurse, List<DoctorClinicEntity> doctor) {
        this.name = name;
        this.open = open;
        this.close = close;
        this.services = services;

        this.Patients=Patients;
        this.nurseEntities=nurse;
        this.doctorClinicEntities=doctor;
        this.appointments = apps;
    }

    public ClinicEntity(ClinicEntity CE) {
        this.name = CE.name;
        this.open = CE.open;
        this.close = CE.close;
        this.services = CE.services;
        this.Patients=CE.Patients;
        this.nurseEntities= CE.nurseEntities;
        this.doctorClinicEntities=CE.doctorClinicEntities;
        this.appointments = CE.appointments;
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

    public List<DoctorClinicEntity> getDoctorClinicEntities() {
        return doctorClinicEntities;
    }

    public void setDoctorClinicEntities(List<DoctorClinicEntity> doctorClinicEntities) {
        this.doctorClinicEntities = doctorClinicEntities;
    }

    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }
}