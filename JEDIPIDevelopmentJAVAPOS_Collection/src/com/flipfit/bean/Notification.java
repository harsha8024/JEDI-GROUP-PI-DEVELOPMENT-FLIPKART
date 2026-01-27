package com.flipfit.bean;

import java.time.LocalDateTime;

public class Notification {
	private String notificationId;
	private String userId;
	private String userType; // CUSTOMER, OWNER, or ADMIN
	private String title;
	private String message;
	private boolean isRead;
	private LocalDateTime createdAt;
	private String type; // Backward compatibility
	
	public Notification() {
		this.isRead = false;
		this.createdAt = LocalDateTime.now();
		this.userType = "CUSTOMER"; // Default to customer
	}
	
	public Notification(String notificationId, String userId, String title, String message, 
			boolean isRead, LocalDateTime createdAt) {
		super();
		this.notificationId = notificationId;
		this.userId = userId;
		this.title = title;
		this.message = message;
		this.isRead = isRead;
		this.createdAt = createdAt;
		this.userType = "CUSTOMER"; // Default to customer
	}

	public String getNotificationId() {
		return notificationId;
	}
	
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isRead() {
		return isRead;
	}
	
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	// Backward compatibility
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", userId=" + userId + 
				", title=" + title + ", message=" + message + ", isRead=" + isRead + 
				", createdAt=" + createdAt + "]";
	}
}
