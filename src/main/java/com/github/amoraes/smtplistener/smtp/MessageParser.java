package com.github.amoraes.smtplistener.smtp;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.james.mime4j.field.address.Address;
import org.apache.james.mime4j.message.BinaryBody;
import org.apache.james.mime4j.message.BodyPart;
import org.apache.james.mime4j.message.Entity;
import org.apache.james.mime4j.message.Message;
import org.apache.james.mime4j.message.Multipart;
import org.apache.james.mime4j.message.TextBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.amoraes.smtplistener.domain.Attachment;

/**
* E-mail message parser to get Text Body, HTML Body and Attachments
* @author Alessandro Moraes, adapted from Denis Lunev
*/
public class MessageParser {
   
   private final Logger log = LoggerFactory.getLogger(this.getClass());
	
   private StringBuffer textBody;
   private StringBuffer htmlBody;
   private List<Attachment> attachments = new ArrayList<Attachment>();
   private String subject; 
   private String replyTo;
   
   
   private List<BodyPart> attachmentsBodyPart = new ArrayList<BodyPart>();
   /**
    *
    * @param fileName
    */
   public void parseMessage(InputStream is) {
       
	   
	   
       textBody = new StringBuffer();
       htmlBody = new StringBuffer();

       try {
           //Create message with stream from file
           //If you want to parse String, you can use:
           //Message mimeMsg = new Message(new ByteArrayInputStream(mimeSource.getBytes()));
           Message mimeMsg = new Message(is);

           //Get some standard headers
           this.subject = mimeMsg.getSubject();
           replyTo = "";
           if(mimeMsg.getReplyTo() != null){
	           for(Address a : mimeMsg.getReplyTo()){
	        	   replyTo += a.getDisplayString()+",";
	           }
	           replyTo = replyTo.substring(0, replyTo.length()-1);
           }

           //If message contains many parts - parse all parts
           if (mimeMsg.isMultipart()) {
               Multipart multipart = (Multipart) mimeMsg.getBody();
               parseBodyParts(multipart);
			} else {
				// If it's single part message, just get text or html body
				if (mimeMsg.getMimeType().equals("text/html")) {
					String html = getTxtPart(mimeMsg);
					htmlBody.append(html);
				} else {
					String text = getTxtPart(mimeMsg);
					textBody.append(text);
				}
			}

           for (BodyPart attach : attachmentsBodyPart) {
               String attName = attach.getFilename();
               String mimeType = attach.getMimeType();
               log.info("Received an attachment: "+attName+" ("+mimeType+")");
               //Create file with specified name
               FileOutputStream fos = new FileOutputStream(attName);
               try {
                   //Get attach stream, write it to file
                   BinaryBody bb = (BinaryBody) attach.getBody();
                   ByteArrayOutputStream bos = new ByteArrayOutputStream();
                   bb.writeTo(bos);
                   Attachment att = new Attachment();
                   att.setFileName(attName);
                   att.setMimeType(mimeType);
                   att.setContent(bos.toByteArray());
                   attachments.add(att);
                   
               } finally {
                   fos.close();
               }
           }

       } catch (IOException ex) {
           ex.fillInStackTrace();
       } finally {
           if (is != null) {
               try {
                   is.close();
               } catch (IOException ex) {
                   ex.printStackTrace();
               }
           }
       }
   }

   /**
    * This method classifies bodyPart as text, html or attached file
    *
    * @param multipart
    * @throws IOException
    */
   private void parseBodyParts(Multipart multipart) throws IOException {
       for (BodyPart part : multipart.getBodyParts()) {
           if (part.isMimeType("text/plain")) {
               String txt = getTxtPart(part);
               textBody.append(txt);
           } else if (part.isMimeType("text/html")) {
               String html = getTxtPart(part);
               htmlBody.append(html);
           } else if (part.getDispositionType() != null && !part.getDispositionType().equals("")) {
               //If DispositionType is null or empty, it means that it's multipart, not attached file
               attachmentsBodyPart.add(part);
           }

           //If current part contains other, parse it again by recursion
           if (part.isMultipart()) {
               parseBodyParts((Multipart) part.getBody());
           }
       }
   }

   /**
    *
    * @param part
    * @return
    * @throws IOException
    */
   private String getTxtPart(Entity part) throws IOException {
       //Get content from body
       TextBody tb = (TextBody) part.getBody();
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       tb.writeTo(baos);
       return new String(baos.toByteArray());
   }

	public String getTextBody() {
		return textBody.toString();
	}
	
	public String getHtmlBody() {
		return htmlBody.toString();
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getReplyTo() {
		return replyTo;
	}

	public List<Attachment> getAttachments(){
		return this.attachments;
	}

}
