package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
@MappedSuperclass
public class EmployeeEntity implements Serializable {

    private int id;
    String employee_name;
    String family_name;
    String mail;

    public EmployeeEntity(int id, String employee_name, String family_name, String mail) {
        this.id = id;
        this.employee_name = employee_name;
        this.family_name = family_name;
        this.mail = mail;
    }

    public EmployeeEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
