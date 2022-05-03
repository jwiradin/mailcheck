package com.sample;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class main {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(new Option("t", true, "mail to"));
        options.addOption(new Option("f", true, "mail from"));
        options.addOption(new Option("s", true, "smtp"));
        options.addOption(new Option("p", true, "smtp port"));
        options.addOption(new Option("u", true, "smtp user"));
        options.addOption(new Option("p", true, "smtp password"));
        options.addOption(new Option("l", true, "start tls"));


        // change accordingly
        String to = "got@gmail.com";

        // change accordingly
        String from = "akash@gmail.com";

        // or IP address
        String host = "localhost";

        // mail id
        final String username = "username@gmail.com"

        // correct password for gmail id
        final String password = "mypassword";

        System.out.println("TLSEmail Start");
        // Get the session object

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // SSL Port
        properties.put("mail.smtp.port", "465");

        // enable authentication
        properties.put("mail.smtp.auth", "true");

        // SSL Factory
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");

        // creating Session instance referenced to
        // Authenticator object to pass in
        // Session.getInstance argument
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {

                    // override the getPasswordAuthentication
                    // method
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication("username",
                                "password");
                    }
                });


//compose the message
        try {
            // javax.mail.internet.MimeMessage class is mostly
            // used for abstraction.
            MimeMessage message = new MimeMessage(session);

            // header field of the header.
            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject("subject");
            message.setText("Hello, aas is sending email ");

            // Send message
            Transport.send(message);
            System.out.println("Yo it has been sent..");
        } catch (
                MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
