package com.ta.airlines.model;

import java.sql.Timestamp;

public class Bookings {
	private Integer bookingId;
	private Integer userId;
	private Integer flightId;
	private Timestamp bookingDate;
	private String status;
	private String ticketNumber;
	private Timestamp createdAt;
	private String flightNumber;
	private String origin;
	private String destination;
	private Timestamp departureTime;
	private Timestamp arrivalTime;

	public Bookings() {

	}

	public Bookings(Integer bookingId, Integer userId, Integer flightId, Timestamp bookingDate, String status,
			String ticketNumber, Timestamp createdAt) {
		this.bookingId = bookingId;
		this.userId = userId;
		this.flightId = flightId;
		this.bookingDate = bookingDate;
		this.status = status;
		this.ticketNumber = ticketNumber;
		this.createdAt = createdAt;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
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

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}