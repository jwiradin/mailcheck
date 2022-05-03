package com.sample;

import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;

import javax.mail.*;
import java.util.Properties;

public class main {

    public static void main(String[] args) {
        Options options = new Options();
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

        String host;
        String username="";
        String password="";
        String starttls;
        String port;

        CommandLineParser clp = new DefaultParser();
        CommandLine cmd = null;
        // Get system properties
        Properties properties = System.getProperties();

        try {
            cmd = clp.parse(options, args);
/*
            if (cmd.hasOption("t")) {
                to = cmd.getOptionValue("t");
            }

            if (cmd.hasOption("f")) {
                from = cmd.getOptionValue("f");
            }

*/

            if (cmd.hasOption("h")) {
                properties.setProperty("mail.smtp.host", cmd.getOptionValue("l"));
            }
            if (cmd.hasOption("o")) {
                properties.put("mail.smtp.port", cmd.getOptionValue("o"));
            }
            if (cmd.hasOption("u") && cmd.hasOption("p")) {
                username = cmd.getOptionValue("u");
                password = cmd.getOptionValue("p");
                properties.put("mail.smtp.auth", "true");
            }

            if(cmd.hasOption("l")){
                properties.setProperty("mail.smtp.ssl.protocols", "TLSV1.2");
                properties.setProperty("mail.smtp.starttls.required", "true");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                System.out.println("TLSEmail Start");
            }
            Authenticator auth = null;

            if(username != ""){
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
                Transport transport = session.getTransport("smtp");
                transport.connect();
                transport.close();
                System.out.println("Connection test completed");
            } catch (NoSuchProviderException ex){
                System.out.println(ex.getMessage());
            } catch (MessagingException mex) {
                mex.printStackTrace();
            } catch (Exception ex){
                ex.getMessage();
            }
        }
        catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
