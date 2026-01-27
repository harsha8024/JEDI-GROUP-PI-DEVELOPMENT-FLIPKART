package com.flipfit.bean;

public class Notification {
	private String notificationId;
	private String message;
	private String type;
	public Notification() {
		
	}
	
	
	public Notification(String notificationId, String message, String type) {
		super();
		this.notificationId = notificationId;
		this.message = message;
		this.type = type;
	}


	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
