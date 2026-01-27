// TODO: Auto-generated Javadoc
package com.flipfit.bean;

import java.time.LocalDateTime;

/**
 * The Class Notification.
 *
 * @author team pi
 * @ClassName "Notification"
 */
public class Notification {

	/** The notification id. */
	private String notificationId;

	/** The user id. */
	private String userId;

	/** The user type. */
	private String userType; // CUSTOMER, OWNER, or ADMIN

	/** The title. */
	private String title;

	/** The message. */
	private String message;

	/** The is read. */
	private boolean isRead;

	/** The created at. */
	private LocalDateTime createdAt;

	/** The type. */
	private String type; // Backward compatibility

	/**
	 * Instantiates a new notification.
	 */
	public Notification() {
		this.isRead = false;
		this.createdAt = LocalDateTime.now();
		this.userType = "CUSTOMER"; // Default to customer
	}

	/**
	 * Instantiates a new notification with the specified parameters.
	 *
	 * @param notificationId the unique identifier for the notification
	 * @param userId         the identifier of the recipient user
	 * @param title          the title of the notification
	 * @param message        the message content of the notification
	 * @param isRead         the status indicating if the notification has been read
	 * @param createdAt      the timestamp when the notification was created
	 */
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

	/**
	 * Gets the notification id.
	 *
	 * @return the notification id
	 */
	public String getNotificationId() {
		return notificationId;
	}

	/**
	 * Sets the notification id.
	 *
	 * @param notificationId the new notification id
	 */
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Checks if is read.
	 *
	 * @return true, if is read
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * Sets the read status.
	 *
	 * @param isRead the new read status
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the user type.
	 *
	 * @return the user type
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the user type.
	 *
	 * @param userType the new user type
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	// Backward compatibility
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns a string representation of the notification.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", userId=" + userId +
				", title=" + title + ", message=" + message + ", isRead=" + isRead +
				", createdAt=" + createdAt + "]";
	}
}
