package com.github.amoraes.smtplistener.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendMail {
	public static void main(String[] args){
		//sending some e-mails to test
		String[] to = {"sanfatec@gmail.com"};
        String[] cc = {"afakename@imaginarydomain.com"};
        String[] bcc = {"mybccmail@classicalmusic.com"};
        String replyTo[] = {"no-reply@something.com"};
        String subject = "Local SMTP Listener Test E-mail";
        String textMessage= "Sent in "+new Date().toString()+"\n Best regards,";
        String from = "mailer@something.com";
		sendMail(from, to, cc, null, replyTo, subject, null, textMessage, null);
		subject = "With HTML inside";
		String htmlMessage = "Hi <b>Alessandro</b>,<br>Hope you all the best.<br/>Cheers,";
		sendMail(from, to, cc, bcc, replyTo, subject, htmlMessage, textMessage, null);
		subject = "Only HTML";
		sendMail(from, to, null, null, replyTo, subject, htmlMessage, null, null);
		subject = "Message with attachment";
		String attachment = "/home/alessandro/attachment.jpg";
		sendMail(from, to, null, null, replyTo, subject, htmlMessage, null, attachment);
	    
		
	}

	private static void sendMail(String from, String[] to, String[] cc, String[] bcc, String[] replyTo, String subject,String htmlMessage, String textMessage, String attachmentFilePath) {
		try {
			Properties mailProps = new Properties();
	        mailProps.put("mail.smtp.host", "localhost");
	        mailProps.put("mail.smtp.port", "25000");
	        Session mailSession = Session.getInstance(mailProps);
	
	        // the main message
	        MimeMessage mimeMessage = new MimeMessage(mailSession);
	        Multipart multipart = new MimeMultipart("mixed");
	        ArrayList<String> toList = new ArrayList<String>();                
	        
	        int size = 0;
	        if (to != null)
	            size += to.length;
	        if (cc != null)
	            size += cc.length;
	        if (bcc != null)
	            size += bcc.length;
	        
	        
	        String sub = from.substring(from.indexOf('@'));
	        
	        //reply to
	        List<InternetAddress> listReplyTo = new ArrayList<InternetAddress>();
	        if (replyTo != null && replyTo.length > 0) {
	            for (String address : replyTo) {
	                if (address == null)
	                    continue;
	                listReplyTo.add(new InternetAddress(address.trim()));
	            }
	        }
	        
	        //from
	        if (from != null) {
	        	
	            if (from.indexOf(sub) >= 0) {
	                mimeMessage.setFrom(new InternetAddress(from));
	                if(!listReplyTo.isEmpty())
	                    mimeMessage.setReplyTo(listReplyTo.toArray(new InternetAddress[listReplyTo.size()]));
	                
	        	} else {
	                mimeMessage.setFrom(new InternetAddress(from));
	                listReplyTo.add(new InternetAddress(from));
	                mimeMessage.setReplyTo(listReplyTo.toArray(new InternetAddress[listReplyTo.size()]));
	            }
	            
	        } else {
	            mimeMessage.setFrom(new InternetAddress(from));
	        }
	        
	        mimeMessage.setSubject(subject);
	        mimeMessage.setSentDate(new Date());
	
	        //To
	        if (to != null && to.length > 0) {
	            for (String para : to) {
	                if (para == null)
	                    continue;
	                toList.add(para.trim());
	            }
	        }
	
	        //Cc
	        if (cc != null && cc.length > 0) {
	            for (String copy : cc) {
	                if (copy == null)
	                    continue;
	                toList.add(copy.trim());
	            }
	        }
	
	        //Bcc
	        if (bcc != null && bcc.length > 0) {
	            for (String cco : bcc) {
	                if (cco == null)
	                    continue;
	                toList.add(cco.trim());
	            }
	        }
	
	
	        
	        // Join html, text and attachments in a single message
	        // HTML + Text
            if(textMessage != null && htmlMessage != null){
	        	MimeBodyPart htmlAndTextPart = new MimeBodyPart();
	        	// Text
	        	MimeBodyPart textPart = new MimeBodyPart();
	        	textPart.setText(textMessage, "UTF-8");
	        	// Html
	        	MimeBodyPart htmlPart = new MimeBodyPart();
	        	htmlPart.setContent(htmlMessage, "text/html");
	            // Junta as partes texto e html como um em alternativa ao outro
	            Multipart multipartAlternative = new MimeMultipart("alternative");
	            multipartAlternative.addBodyPart(textPart);
	            multipartAlternative.addBodyPart(htmlPart);
	            
	            htmlAndTextPart.setContent(multipartAlternative);
	            
	            multipart.addBodyPart(htmlAndTextPart);
	        }
            //Only Text
	        else if(textMessage != null){
	        	MimeBodyPart textPart = new MimeBodyPart();
	        	textPart.setText(textMessage, "UTF-8");
	        	multipart.addBodyPart(textPart);
	        }
            //Only HTML
	        else if(htmlMessage != null){
	        	MimeBodyPart textPart = new MimeBodyPart();
	        	textPart.setContent(htmlMessage, "text/html");
	        	multipart.addBodyPart(textPart);
	        }
            //Attachment
	        if(attachmentFilePath != null){
	        	MimeBodyPart attachmentPart = new MimeBodyPart();
	        	DataSource dataSource = new FileDataSource(attachmentFilePath);
	        	attachmentPart.setDataHandler(new DataHandler(dataSource));
	        	attachmentPart.setFileName(dataSource.getName());
	        	multipart.addBodyPart(attachmentPart);
	        }	
            // Adds the multipart to the message
            mimeMessage.setContent(multipart);
            
            //try to send the e-mails
            for (String targetAddress : toList) {
            	try {
            		mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(targetAddress.trim()));
                    Transport.send(mimeMessage);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
