package com.sample;

import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class main {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("t")
            .longOpt("mailto")
            .hasArg(true)
            .desc("mail to")
            .required(true)
            .build()
        );
        options.addOption(Option.builder("f")
                .longOpt("mailfrom")
                .hasArg(true)
                .desc("mail from")
                .required(true)
                .build()
        );
        options.addOption(Option.builder("s")
                .longOpt("smpthost")
                .hasArg(true)
                .desc("smtphost")
                .required(true)
                .build()
        );
        options.addOption(Option.builder("o")
                .longOpt("smtpport")
                .hasArg(true)
                .desc("smtp port")
                .required(true)
                .build()
        );
        options.addOption(Option.builder("u")
                .longOpt("user")
                .hasArg(true)
                .desc("smpt login id")
                .build()
        );
        options.addOption(Option.builder("p")
                .longOpt("password")
                .hasArg(true)
                .desc("smtp password")
                .build()
        );
        options.addOption(Option.builder("l")
                .longOpt("starttls")
                .desc("starttls")
                .build()
        );
        String to;
        String from;
        String host;
        String username;
        String password;
        String starttls;
        String port;

        CommandLineParser clp = new DefaultParser();
        CommandLine cmd = null;
        // Get system properties
        Properties properties = System.getProperties();

        try {
            cmd = clp.parse(options, args);

            if (cmd.hasOption("t")) {
                to = cmd.getOptionValue("t");
            }

            if (cmd.hasOption("f")) {
                from = cmd.getOptionValue("f");
            }

            if (cmd.hasOption("h")) {
                properties.setProperty("mail.smtp.host", cmd.getOptionValue("l"));
            }
            if (cmd.hasOption("o")) {
                properties.put("mail.smtp.port", cmd.getOptionValue("o"));
            }
            if (cmd.hasOption("u")) {
                username = cmd.getOptionValue("u");
            }

            if (cmd.hasOption("p")) {
                password = cmd.getOptionValue("p");
            }

            System.out.println("TLSEmail Start");
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
        catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
