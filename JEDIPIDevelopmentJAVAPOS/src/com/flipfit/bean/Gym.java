package com.flipfit.bean;

import java.util.ArrayList;
import java.util.List;

public class Gym {
	private String gymId;
	private String gymName;
	private String location;
	private boolean isApproved;
	// New field to store gym-specific slots
	private List<String> slots = new ArrayList<>(); 

	public Gym() {}

	public Gym(String gymId, String gymName, String location, boolean isApproved) {
		this.gymId = gymId;
		this.gymName = gymName;
		this.location = location;
		this.isApproved = isApproved;
	}

	// Getter and Setter for slots
	public List<String> getSlots() {
		return slots;
	}

	public void setSlots(List<String> slots) {
		this.slots = slots;
	}

	// Existing Getters and Setters...
	public String getGymId() { return gymId; }
	public void setGymId(String gymId) { this.gymId = gymId; }
	public String getGymName() { return gymName; }
	public void setGymName(String gymName) { this.gymName = gymName; }
	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
	public boolean isApproved() { return isApproved; }
	public void setApproved(boolean isApproved) { this.isApproved = isApproved; }
}