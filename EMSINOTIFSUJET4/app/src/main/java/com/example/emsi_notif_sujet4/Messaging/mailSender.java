package com.example.emsi_notif_sujet4.Messaging;

import com.example.emsi_notif_sujet4.Models.Document;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mailSender {

    public static void sendMail(String recepient,String namePerson,Document document) throws Exception
    {
        System.out.println("preparation de l envoie de gmail ...");
        Properties properties =new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        final String MyAccountEmail="nokapp.emsi@gmail.com";
        final String Password="soit zero soit un";

        Session session= Session.getInstance(properties,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyAccountEmail, Password);
            }
        });


        Message message= preparedMessage(session,MyAccountEmail,recepient,namePerson,document);

        Transport.send(message);
        System.out.println("Message envoyer !!!");
        System.out.println("***********************************");
        System.out.println();




    }
    private static Message preparedMessage(Session session, String myAccountEmail, String recepient, String namePerson, Document document)
    {
        Message message= new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject( namePerson);
            message.setText(document.toString());

            return message;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}
