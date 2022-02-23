package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.AppointmentEntity;
import il.cshaifasweng.OCSFMediatorExample.entities.VaccineAppointmentEntity;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
//    *
//     * Utility method to send simple HTML email
//     * @param session
//     * @param toEmail
//     * @param subject
//     * @param body

    public static void sendEmail(String toEmail, String subject, String body){
        try
        {

            final String fromEmail = "engineersoftware705@gmail.com"; //requires valid gmail id
            final String password = " "; // correct password for gmail id

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("engineersoftware705@gmail.com"));
            //  "Sirtya G8"
            msg.setReplyTo(InternetAddress.parse("engineersoftware705@gmail.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendEmailAppointment(AppointmentEntity app){
        try
        {

            final String fromEmail = "engineersoftware705@gmail.com"; //requires valid gmail id
            final String password = " "; // correct password for gmail id

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("engineersoftware705@gmail.com", "clinics"));

            msg.setReplyTo(InternetAddress.parse("engineersoftware705@gmail.com", false));

            msg.setSubject("reminder for appointment", "UTF-8");
            String body="your appointment in,"+ app.getClinic().getName()+"\n"+" at :"+ app.getDate()+"\n"+" with doctor: "+ app.getDoctor().getFamily_name()+"\n";
            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(app.getPatient().getMail(), false));
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendEmailVacAppointment(VaccineAppointmentEntity app){
        try
        {

            final String fromEmail = "engineersoftware705@gmail.com"; //requires valid gmail id
            final String password = " "; // correct password for gmail id

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("engineersoftware705@gmail.com", "clinics"));

            msg.setReplyTo(InternetAddress.parse("engineersoftware705@gmail.com", false));

            msg.setSubject("reminder for vaccine", "UTF-8");
            String body="your vaccine in:"+ app.getClinic().getName()+"\n"+" at :"+ app.getDate()+"\n";
            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(app.getPatient().getMail(), false));
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
