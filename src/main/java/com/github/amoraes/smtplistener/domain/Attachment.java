package com.github.amoraes.smtplistener.domain;

/**
 * Entity representing an e-mail attached file
 * @author Alessandro Moraes
 */
public class Attachment {
	
	private int id;
	
	private String fileName;
	
	private String mimeType;
	
	private byte[] content;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	/**
	 * This is intentionally not a standard get method to not be shown as a attribute in the rest layer
	 * @return byte[] binary content of the file
	 */
	public byte[] retrieveContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public long getSize(){
		return content.length;
	}
	
	
	
}
