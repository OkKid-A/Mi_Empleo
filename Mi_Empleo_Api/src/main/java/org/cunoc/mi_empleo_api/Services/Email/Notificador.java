package org.cunoc.mi_empleo_api.Services.Email;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;

import java.sql.SQLException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class Notificador {

        final String senderEmail = "miguelqueme202031398@cunoc.edu.gt";
        final String senderPassword = "upeq yray muwj cwer";
        final String emailSMTPserver = "smtp.gmail.com";
        final String emailServerPort = "587";
        String receiverEmail = null;
        String emailSubject = null;
        String emailBody = null;
        private Conector conector;

        public Notificador(Conector conector) {
            this.conector = conector;
        }


        public void enviarEmailAUsuario(String receiverEmail, String Subject, String msg) throws SQLException, InvalidDataException {
                Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", emailSMTPserver);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", emailSMTPserver);
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");


            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, senderPassword);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(receiverEmail));
                    message.setSubject(Subject);
                    message.setText(msg);

                    Transport.send(message);

                    System.out.println("Email sent successfully to " + receiverEmail);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    throw new InvalidDataException();
                }
            }

    }

