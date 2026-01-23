package com.flipfit.bean;

import java.time.LocalDate;

public class Schedule {
	
	private String scheduleId;
	private LocalDate date;
	private boolean isUpdated;
	
	public Schedule() {
		
	}
	

	public Schedule(String scheduleId, LocalDate date, boolean isUpdated) {
		super();
		this.scheduleId = scheduleId;
		this.date = date;
		this.isUpdated = isUpdated;
	}


	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
}
