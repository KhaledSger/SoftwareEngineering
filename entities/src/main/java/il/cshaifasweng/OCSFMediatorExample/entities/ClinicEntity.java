package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

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

    private String report1;
    private long[] reports2;
    private String report2;

    @Column(length = 65555)
    @NotNull
    private long[][] reports;

    @OneToMany (mappedBy = "clinic")
    private List<PatientEntity> Patients;

    @OneToMany (mappedBy = "clinic")
    private List<NurseEntity> nurseEntities;

    @OneToMany(mappedBy = "clinic")
    private  List<DoctorClinicEntity> doctorClinicEntities;

    @OneToOne(mappedBy = "clinic",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ManagerEntity manager;


    @OneToMany (fetch=FetchType.LAZY , mappedBy = "clinic")
    private List<AppointmentEntity> appointments;

    @OneToMany (fetch=FetchType.LAZY , mappedBy = "clinic")
    private List<VaccineAppointmentEntity> vac_appointments;

    @OneToMany (mappedBy = "clinic")
    private List<LabWorkerEntity> labWorkers;

    public ClinicEntity(String name, String open, String close, String[] services,List<PatientEntity> Patients) {
        this.name = name;
        this.open = open;
        this.close = close;
        this.services = services;
        this.reports = new long[7][6]; // rows for days and columns for services
        this.Patients=new ArrayList<PatientEntity>();
        this.nurseEntities=new ArrayList<NurseEntity>();
        this.doctorClinicEntities=new ArrayList<DoctorClinicEntity>();
        this.labWorkers = new ArrayList<LabWorkerEntity>();
        this.appointments=new ArrayList<AppointmentEntity>();
        this.vac_appointments = new ArrayList<VaccineAppointmentEntity>();
        this.report1="";
        this.reports2 = new long[4];
        this.report2 = "";
    }


    public ClinicEntity(ClinicEntity CE) {
        this.name = CE.name;
        this.open = CE.open;
        this.close = CE.close;
        this.services = CE.services;
        this.Patients=CE.Patients;
        this.nurseEntities= CE.nurseEntities;
        this.doctorClinicEntities=CE.doctorClinicEntities;
        this.manager=CE.manager;
        this.labWorkers = CE.labWorkers;
    }

    public long[] getReports2() {
        return reports2;
    }

    public void setReports2(long[] reports2) {
        this.reports2 = reports2;
    }

    public String getReport2() {
        return report2;
    }

    public void setReport2(String report2) {
        this.report2 = report2;
    }

    public ClinicEntity() {
    }

    public ClinicEntity(String name) {
        this.name = name;
    }

    public long[][] getReports() {
        return reports;
    }

    public String getReport1() {
        return report1;
    }

    public void setReport1(String report) {
        this.report1 = report;
    }

    public void setReports(long[][] reports) {
        this.reports = reports;
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

    public ManagerEntity getManager() {
        return manager;
    }

    public void setManager(ManagerEntity manager) {
        this.manager = manager;
         manager.setClinic(this);
    }

    public void updateManager(ManagerEntity manager)
    {
        this.manager= manager;
    }
    public List<LabWorkerEntity> getLabWorkers() {
        return labWorkers;
    }

    public void setLabWorkers(List<LabWorkerEntity> labWorkers) {
        this.labWorkers = labWorkers;
    }
    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    public List<VaccineAppointmentEntity> getVac_appointments() {
        return vac_appointments;
    }

    public void setVac_appointments(List<VaccineAppointmentEntity> vac_appointments) {
        this.vac_appointments = vac_appointments;
    }
}