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

    @Override
    public void run() {
        //SimpleServer.session.getTransaction().begin();
        while (true) {
            //SimpleServer.GetAllAppointments()
            SimpleServer.Appointments = (ArrayList<AppointmentEntity>)SimpleServer.GetAllAppointments();
            System.out.println("in the thread num: " + this.getId());
            System.out.println(SimpleServer.Appointments.size());
            for (AppointmentEntity app : SimpleServer.Appointments) {
                if ((app.isReserved() == true) && (app.getPatient() == null)) {
                    app.setReserved(false);
                    SimpleServer.session.getTransaction().begin();
                    SimpleServer.session.saveOrUpdate(app);
                    SimpleServer.session.flush();
                    SimpleServer.session.getTransaction().commit();

                }
            }
//            if(LocalDateTime.now().getHour()==23 && LocalDateTime.now().getMinute()==55)
//            {
//                SimpleServer.UpdateReports();
//                LocalDate localDate=LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
//                int day_of_week= getDayNumberNew(localDate);
//                if(day_of_week==6)
//                {
//                    SimpleServer.UpdateWeeklyReports();
//                }
//            }
            try {
                //sleep(300000); //5 minutes
               sleep(10000); // 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return (day.getValue());
    }
}
