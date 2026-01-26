/**
 * 
 */
package com.flipfit.bean;

/**
 * 
 */
public class Gym {
	private String gymId;
	private String gymName;
	private String location;
	private String gymOwnerId;
	private boolean isApproved;
	
	public Gym() {
		
	}
	
	public Gym(String gymId, String gymName, String location, String gymOwnerId, boolean isApproved) {
		super();
		this.gymId = gymId;
		this.gymName = gymName;
		this.location = location;
		this.gymOwnerId = gymOwnerId;
		this.isApproved = isApproved;
	}
	public String getGymId() {
		return gymId;
	}
	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	public String getGymName() {
		return gymName;
	}
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getGymOwnerId() {
		return gymOwnerId;
	}
	public void setGymOwnerId(String gymOwnerId) {
		this.gymOwnerId = gymOwnerId;
	}
	
	
}
