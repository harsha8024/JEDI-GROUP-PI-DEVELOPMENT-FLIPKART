package com.flipfit.bean;

import java.time.LocalDateTime;

public class Registration {
	private String registrationId;
	private String userId;
	private String roleId;
	private LocalDateTime registrationDate;
	private String status;
	private String approvedBy;
	private LocalDateTime approvalDate;
	
	public Registration() {
		this.registrationDate=LocalDateTime.now();
		this.status="PENDING";
	}

	public Registration(String registrationId, String userId, String roleId) {
		super();
		this.registrationId = registrationId;
		this.userId = userId;
		this.roleId = roleId;
		this.registrationDate=LocalDateTime.now();
		this.status="PENDING";
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public LocalDateTime getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDateTime approvalDate) {
		this.approvalDate = approvalDate;
	}

	@Override
	public String toString() {
		return "Registration [registrationId=" + registrationId + ", userId=" + userId + ", roleId=" + roleId
				+ ", registrationDate=" + registrationDate + ", status=" + status + ", approvedBy=" + approvedBy
				+ ", approvalDate=" + approvalDate + "]";
	}
	
	
	
	
}
