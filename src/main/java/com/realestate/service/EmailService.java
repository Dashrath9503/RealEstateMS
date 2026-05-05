package com.realestate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendInquiryNotification(String ownerEmail, String ownerName,
                                        String propertyTitle, String buyerName,
                                        String buyerEmail, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(ownerEmail);
        mail.setSubject("New Inquiry for Your Property: " + propertyTitle);
        mail.setText(
            "Hello " + ownerName + ",\n\n" +
            "You have received a new inquiry for your property: " + propertyTitle + "\n\n" +
            "From: " + buyerName + " (" + buyerEmail + ")\n\n" +
            "Message:\n" + message + "\n\n" +
            "Please login to your dashboard to respond.\n\n" +
            "Regards,\nReal Estate MS Team"
        );
        mailSender.send(mail);
    }

    public void sendPropertyApprovalEmail(String ownerEmail, String ownerName, String propertyTitle) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(ownerEmail);
        mail.setSubject("Your Property Has Been Approved!");
        mail.setText(
            "Hello " + ownerName + ",\n\n" +
            "Great news! Your property \"" + propertyTitle + "\" has been approved " +
            "and is now live on Real Estate MS.\n\n" +
            "Regards,\nReal Estate MS Team"
        );
        mailSender.send(mail);
    }
    
    public void sendReplyNotification(
            String buyerEmail,
            String buyerName, 
            String replyMessage,
            String propertyTitle) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(buyerEmail);
            message.setSubject(
                "Owner replied to your inquiry - " 
                + propertyTitle);
            message.setText(
                "Dear " + buyerName + ",\n\n" +
                "Owner ne aapki inquiry ka reply diya hai!\n\n" +
                "Property: " + propertyTitle + "\n\n" +
                "Reply: " + replyMessage + "\n\n" +
                "Thanks,\nReal Estate Team"
            );
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(
                "Reply notification failed: " 
                + e.getMessage());
        }
    }
}
