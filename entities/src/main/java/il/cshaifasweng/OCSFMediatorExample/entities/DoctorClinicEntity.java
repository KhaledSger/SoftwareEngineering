package il.cshaifasweng.OCSFMediatorExample.entities;





import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class DoctorClinicEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DoctorClinic_id")
    private int DoctorClinic_id;
    @ManyToOne
    @JoinColumn(name="doctor_id")
    DoctorEntity doctor;
    
    @ManyToOne
    @JoinColumn(name="clinic_id")
    ClinicEntity clinic;



    String day_hours;


    public DoctorClinicEntity(DoctorEntity doctor, ClinicEntity clinic, ArrayList<String> day_hour) {
        this.doctor = doctor;
        this.clinic = clinic;
        setDay_hours(day_hour);

    }
    public DoctorClinicEntity() {
    }

    public void setDay_hours(ArrayList<String> day_hour) {
        String ListToString = "";
        for(int i = 0; i < day_hour.size(); i++){
            ListToString += day_hour.get(i);
            ListToString += "@&%@";
        }
        this.day_hours = ListToString;

    }

    public String getDay_hours() {
        return day_hours;
    }

    public ArrayList<String> getWorkingHours(){
        String workingHours = getDay_hours();
        return (ArrayList<String>) Arrays.stream(workingHours.split("@&%@")).toList();
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
        doctor.getDoctorClinicEntities().add(this);
    }

    public ClinicEntity getClinic() {
        return clinic;
    }

    public void setClinic(ClinicEntity clinic) {
        this.clinic = clinic;
        clinic.getDoctorClinicEntities().add(this);
    }

//    public Map<Integer, String> getDay_hours() {
//        return day_hours;
//    }

//    public void setDay_hours(Map<Integer, String> day_hours) {
//        this.day_hours = day_hours;
//    }

    public int getDoctorClinic_id() {
        return DoctorClinic_id;
    }
}
