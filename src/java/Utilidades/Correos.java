/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Alex
 */
public class Correos {

    private final Properties properties = new Properties();

    private String password = "comeren21";

    private Session session;

    private void init() {
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.mail.sender", "correo.comeren@outlook.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.auth", "true");

        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("correo.comeren@outlook.com", password);
            }
        });

    }

    /**
     * Envio un correo con el asunto, texto y destinatario introducido por
     * parametro
     *
     * @param asunto
     * @param texto
     * @param destinatario
     */
    public void sendEmail(String asunto, String texto, String destinatario) {

        init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(texto);
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), "comeren21");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            System.out.println("Error al enviar correo" + me.getLocalizedMessage());
        }

    }

}
