package com.sample;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailClient {

    private MimeMessage message;

    public MailClient(String filename) throws IOException, MessagingException {
        InputStream mail = new FileInputStream(filename);
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        this.message = new MimeMessage(session, mail);

        String contentType = message.getContentType();
        if(contentType.contains("multipart")){
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++){
                MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);

                System.out.println(part.getDisposition());
            }
        }
    }

    public MimeMessage getMessage(){
        return message;
    };
}
