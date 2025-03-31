package com.example.capstone;
import android.os.AsyncTask;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmailTask extends AsyncTask<Void, Void, Void> {

    private String toEmail;
    private double latitude;
    private double longitude;

    public SendEmailTask(String toEmail, double latitude, double longitude) {
        this.toEmail = toEmail;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // SMTP Server Settings
            String host = "smtp.sendgrid.net";
            String username = "apikey";  // SendGrid's SMTP requires 'apikey' as the username
            String password = "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";  // Your SendGrid API key

            // Setting up the properties for the SMTP connection
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");  // Use 587 for TLS
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // Create the session with authentication
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Compose the email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("wheelchairlocation@gmail.com"));  // Sender's email address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));  // Recipient's email address
            message.setSubject("Emergency Location");
            message.setText("Current location:\nLatitude: " + latitude + "\nLongitude: " + longitude);

            // Send the email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
