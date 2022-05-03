package com.sample;

import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;

import javax.mail.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Properties;

public class main {

    public static void main(String[] args) {
        Options options = new Options();

        options.addOption(Option.builder("h")
            .longOpt("help")
            .desc("print help")
            .build()
        );
/*
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
*/
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
        options.addOption(Option.builder("i")
                .longOpt("isssl")
                .desc("is ssl")
                .build()
        );
        options.addOption(Option.builder("ts")
                .longOpt("truststore")
                .hasArg(true)
                .desc("trustsore")
                .build()
        );
        options.addOption(Option.builder("tsp")
                .longOpt("truststorepassword")
                .hasArg(true)
                .desc("truststorepassword")
                .build()
        );
        String username="";
        String password="";

        CommandLineParser clp = new DefaultParser();
        CommandLine cmd = null;
        // Get system properties
        Properties properties = System.getProperties();
        Properties p = System.getProperties();

        try {
            cmd = clp.parse(options, args);
            p.setProperty("javax.net.debug", "all");
            p.setProperty("javax.mail.smtp.debug", "true");

            if(cmd.hasOption("h")){
                HelpFormatter help = new HelpFormatter();
                help.printHelp("help", options);
                return;
            }

            if (cmd.hasOption("ts") && cmd.hasOption("tsp")) {
                p.setProperty("javax.net.ssl.trustStore", cmd.getOptionValue("ts"));
                p.setProperty("javax.net.ssl.trustStorePassword", cmd.getOptionValue("tsp"));
            }

            if (cmd.hasOption("s")) {
                properties.setProperty("mail.smtp.host", cmd.getOptionValue("s"));
            }
            if (cmd.hasOption("o")) {
                properties.put("mail.smtp.port", cmd.getOptionValue("o"));
            }
            if (cmd.hasOption("u") && cmd.hasOption("p")) {
                username = cmd.getOptionValue("u");
                password = cmd.getOptionValue("p");
                properties.put("mail.smtp.auth", "true");
            }

            if(cmd.hasOption("i")){
                properties.setProperty("mail.smtp.ssl.enable", "true");
            }
            if (cmd.hasOption("l")) {
                properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
                properties.setProperty("mail.smtp.starttls.required", "true");
               // properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                System.out.println("TLSEmail Start");
            }
            Authenticator auth = null;

            if (username != "") {
                String finalUsername = username;
                String finalPassword = password;
                auth = new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(finalUsername,
                                finalPassword);
                    }
                };
            }
            try {
                Session session = Session.getDefaultInstance(properties, auth);
                session.setDebug(true);
                Transport transport = session.getTransport("smtp");
                transport.connect();
                transport.close();
                System.out.println("Connection test completed");

            } catch (NoSuchProviderException ex) {
                ex.printStackTrace();
            } catch (MessagingException mex) {
                mex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
