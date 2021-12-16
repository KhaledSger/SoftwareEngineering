package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Clinc")
public class ClincEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Clnic_id")
    private int id;
    private String name;
    private String open;
    private String close;
    private String[] services;

    public ClincEntity(String name, String open, String close, String[] services) {
        this.name = name;
        this.open = open;
        this.close = close;
        this.services = services;
    }

    public ClincEntity(ClincEntity CE) {
        this.name = CE.name;
        this.open = CE.open;
        this.close = CE.close;
        this.services = CE.services;
    }

    public ClincEntity() {
    }

    public ClincEntity(String name) {
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
}

