package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.util.List;

public class MyThread extends Thread {
    private static List<AppointmentEntity> Appointments = SimpleServer.GetAllAppointments();

    @Override
    public void run() {
        while (true) {
            System.out.println("in the thread num: " + this.getId());
            for (AppointmentEntity app : Appointments) {
                if ((app.isReserved() == true) && (app.getPatient() == null)) {
                    app.setReserved(false);
                    //need to upddate the app
                    //System.out.println("in the thread!!");

                }
            }
            try {
                //sleep(300000);
                sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();


            }


        }

    }
}

