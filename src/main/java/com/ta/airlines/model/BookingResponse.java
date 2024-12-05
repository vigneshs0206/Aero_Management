package com.ta.airlines.model;

import java.sql.Timestamp;

public class BookingResponse {
	private int id;
	private int userId;
	private int flightId;
	private Timestamp bookingDate;
	private String status;
	private String ticketNumber;
	private Timestamp createdAt;

	public BookingResponse(int id, int userId, int flightId, Timestamp bookingDate, String status, String ticketNumber,
			Timestamp createdAt) {
		this.id = id;
		this.userId = userId;
		this.flightId = flightId;
		this.bookingDate = bookingDate;
		this.status = status;
		this.ticketNumber = ticketNumber;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public Timestamp getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Timestamp bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}