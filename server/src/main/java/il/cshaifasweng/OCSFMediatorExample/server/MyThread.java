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
import java.util.*;

import java.util.List;

public class MyThread extends Thread {

    @Override
    public void run() {

        //SimpleServer.session.getTransaction().begin();
        while (true) {
            //Date date=new Date();
            //date.getTime();

            //SimpleServer.GetAllAppointments()
            SimpleServer.Appointments = (ArrayList<AppointmentEntity>)SimpleServer.GetAllAppointments();
            System.out.println("in the thread num: " + this.getId());
            System.out.println(SimpleServer.Appointments.size());

            //Appointment
            for (AppointmentEntity app : SimpleServer.Appointments) {
                if ((app.isReserved() == true) && (app.getPatient() == null)) {
                    app.setReserved(false);
                    SimpleServer.session.getTransaction().begin();
                    SimpleServer.session.saveOrUpdate(app);
                    SimpleServer.session.flush();
                    SimpleServer.session.getTransaction().commit();

                }
                //send mail before 24h
                //Date date_now=new Date();
                Calendar app_cal=Calendar.getInstance();
                app_cal.set(app.getDate().getYear(),app.getDate().getMonthValue(),app.getDate().getDayOfMonth(),app.getDate().getHour(),app.getDate().getMinute(),app.getDate().getSecond());
                //now.setTime(date_now);
                //now.getTime().
                //LocalDateTime now=LocalDateTime.now();
                long day_1 = 1000 * 60 * 60 * 24;
                Date now_date_mills = new Date(System.currentTimeMillis());

                long app_time_in_mills=app_cal.getTimeInMillis();

                if((app.isReserved())&&(app.getPatient()!=null)&&(((now_date_mills.getTime()+day_1+30000)>app_time_in_mills))&&(now_date_mills.getTime()+day_1-30000)<(app_time_in_mills))
                {
                    EmailUtil.sendEmailAppointment(app);

                }
            }

            //vaccine Appointment
            for (VaccineAppointmentEntity vac_app : SimpleServer.vaccine_Appointments) {

                //send mail before 24h

                Calendar vac_app_cal=Calendar.getInstance();
                vac_app_cal.set(vac_app.getDate().getYear(),vac_app.getDate().getMonthValue(),vac_app.getDate().getDayOfMonth(),vac_app.getDate().getHour(),vac_app.getDate().getMinute(),vac_app.getDate().getSecond());
                //now.setTime(date_now);
                //now.getTime().
                //LocalDateTime now=LocalDateTime.now();
                long day_1 = 1000 * 60 * 60 * 24;
                Date now_date_mills = new Date(System.currentTimeMillis());

                long app_time_in_mills=vac_app_cal.getTimeInMillis();

                if((vac_app.isReserved())&&(vac_app.getPatient()!=null)&&(((now_date_mills.getTime()+day_1+30000)>app_time_in_mills))&&(now_date_mills.getTime()+day_1-30000)<(app_time_in_mills))
                {
                    EmailUtil.sendEmailVacAppointment(vac_app);

                }
            }
            try {
                //sleep(300000); 5 minutes
                sleep(30000); // 30 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
