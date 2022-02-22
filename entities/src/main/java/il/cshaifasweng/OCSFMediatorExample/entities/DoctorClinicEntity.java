package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DoctorClinicEntity implements Serializable {


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
          setDoctor(doctor); // TODO change tp set
          setClinic(clinic); // TODO change tp set
          setDay_hours(day_hour);
        //  doctor.getDoctorClinicEntities().add(this);
        //  clinic.getDoctorClinicEntities().add(this);

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

    public List<String> getWorkingHours(){
        String workingHours = getDay_hours();

        return List.of(workingHours.split("@&%@"));
    }
    public ArrayList<LocalTime> GetWorkingDateTime() {
       List<String> work_hours = getWorkingHours();
        ArrayList<LocalTime> Dates = new ArrayList<LocalTime>();
        for (int i = 0; i < work_hours.size(); i++) {
           List<String> tmp = List.of(work_hours.get(i).split("-"));

          /*  if (work_hours.get(i).length() == 0) {
                Dates.add(LocalTime.parse("00:00"));
                Dates.add(LocalTime.parse("00:00"));
            } else {*/
                Dates.add(LocalTime.parse(tmp.get(0)));
                Dates.add(LocalTime.parse(tmp.get(1)));
           /* }*/

        }
        return Dates;
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

//    public Map<Integer, String> getDay_hours() { // TODO check
//        return day_hours;
//    }

//    public void setDay_hours(Map<Integer, String> day_hours) { // TODO check
//        this.day_hours = day_hours;
//    }

    public int getDoctorClinic_id() {
        return DoctorClinic_id;
    }
}
