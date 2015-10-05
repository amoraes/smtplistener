package com.github.amoraes.smtplistener.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an e-mail message + content
 * @author Alessandro Moraes
 */
public class EmailMessageWithContent extends EmailMessage {
	
	private String textBody;
	private String htmlBody;
	
	private List<Attachment> attachments = new ArrayList<Attachment>();
	
	public String getTextBody() {
		return textBody;
	}
	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}
	public String getHtmlBody() {
		return htmlBody;
	}
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	
	
}
