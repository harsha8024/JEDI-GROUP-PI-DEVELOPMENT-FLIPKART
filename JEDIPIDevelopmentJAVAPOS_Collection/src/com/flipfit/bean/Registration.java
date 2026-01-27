// TODO: Auto-generated Javadoc
package com.flipfit.bean;

import java.time.LocalDateTime;

/**
 * The Class Registration.
 *
 * @author team pi
 * @ClassName "Registration"
 */
public class Registration {

	/** The registration id. */
	private String registrationId;

	/** The user id. */
	private String userId;

	/** The role id. */
	private String roleId;

	/** The registration date. */
	private LocalDateTime registrationDate;

	/** The status. */
	private String status;

	/** The approved by. */
	private String approvedBy;

	/** The approval date. */
	private LocalDateTime approvalDate;

	/**
	 * Instantiates a new registration.
	 */
	public Registration() {
		this.registrationDate = LocalDateTime.now();
		this.status = "PENDING";
	}

	/**
	 * Instantiates a new registration with the specified parameters.
	 *
	 * @param registrationId the unique identifier for the registration
	 * @param userId         the identifier of the user registering
	 * @param roleId         the identifier of the role being assigned to the user
	 */
	public Registration(String registrationId, String userId, String roleId) {
		super();
		this.registrationId = registrationId;
		this.userId = userId;
		this.roleId = roleId;
		this.registrationDate = LocalDateTime.now();
		this.status = "PENDING";
	}

	/**
	 * Gets the registration id.
	 *
	 * @return the registration id
	 */
	public String getRegistrationId() {
		return registrationId;
	}

	/**
	 * Sets the registration id.
	 *
	 * @param registrationId the new registration id
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
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
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role id.
	 *
	 * @param roleId the new role id
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * Gets the registration date.
	 *
	 * @return the registration date
	 */
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets the registration date.
	 *
	 * @param registrationDate the new registration date
	 */
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the approved by.
	 *
	 * @return the approved by
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * Sets the approved by.
	 *
	 * @param approvedBy the new approved by
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * Gets the approval date.
	 *
	 * @return the approval date
	 */
	public LocalDateTime getApprovalDate() {
		return approvalDate;
	}

	/**
	 * Sets the approval date.
	 *
	 * @param approvalDate the new approval date
	 */
	public void setApprovalDate(LocalDateTime approvalDate) {
		this.approvalDate = approvalDate;
	}

	/**
	 * Returns a string representation of the registration.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "Registration [registrationId=" + registrationId + ", userId=" + userId + ", roleId=" + roleId
				+ ", registrationDate=" + registrationDate + ", status=" + status + ", approvedBy=" + approvedBy
				+ ", approvalDate=" + approvalDate + "]";
	}

}
