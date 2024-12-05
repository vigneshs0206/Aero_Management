package com.ta.airlines.model;

import java.sql.Timestamp;

import java.math.BigDecimal;

public class Flights {
	 public Flights(int id, String flightNumber, String origin, String destination, Timestamp departureTime,
			Timestamp arrivalTime, int seatsAvailable, BigDecimal price, Timestamp createdAt) {
		this.id = id;
		this.flightNumber = flightNumber;
		this.origin = origin;
		this.destination = destination;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatsAvailable = seatsAvailable;
		this.price = price;
		this.createdAt = createdAt;
	}
	 public Flights() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	private int id;
	    private String flightNumber;
	    private String origin;
	    private String destination;
	    private Timestamp departureTime;
	    private Timestamp arrivalTime;
	    private int seatsAvailable;
	    private BigDecimal price;
	    private Timestamp createdAt;
}
