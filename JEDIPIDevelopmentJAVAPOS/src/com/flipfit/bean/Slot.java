package com.flipfit.bean;

import java.time.LocalTime;

public class Slot {
	
	private String slotId;
	private LocalTime startTime;
	private LocalTime endTime;
	private int capacity;
	private int availableSeats;
	public Slot() {
		
	}
	public Slot(String slotId, LocalTime startTime, LocalTime endTime, int capacity, int availableSeats) {
		super();
		this.slotId = slotId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		this.availableSeats = availableSeats;
	}
	public String getSlotId() {
		return slotId;
	}
	public void setSlotId(String slotId) {
		this.slotId = slotId;
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
	
	
	
}
