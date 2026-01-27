package com.flipfit.bean;

import java.time.LocalTime;

public class Slot {
	
	private String slotId;
	private String gymId;
	private LocalTime startTime;
	private LocalTime endTime;
	private int capacity;
	private int availableSeats;
	private boolean isApproved;
	
	public Slot() {
		
	}
	
	public Slot(String slotId, String gymId, LocalTime startTime, LocalTime endTime, int capacity, int availableSeats) {
		super();
		this.slotId = slotId;
		this.gymId = gymId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		this.availableSeats = availableSeats;
		this.isApproved = false;
	}
	
	public String getSlotId() {
		return slotId;
	}
	
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}
	
	public String getGymId() {
		return gymId;
	}
	
	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getAvailableSeats() {
		return availableSeats;
	}
	
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	public boolean isApproved() {
		return isApproved;
	}
	
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	@Override
	public String toString() {
		return "Slot [slotId=" + slotId + ", gymId=" + gymId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", capacity=" + capacity + ", availableSeats=" + availableSeats + ", isApproved=" + isApproved + "]";
	}
	
}
