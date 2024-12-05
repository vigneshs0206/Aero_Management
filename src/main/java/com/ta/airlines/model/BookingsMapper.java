package com.ta.airlines.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BookingsMapper implements RowMapper<Bookings> {

	public Bookings mapRow(ResultSet rs, int i) throws SQLException {
		Bookings booking = new Bookings();
		booking.setBookingId(rs.getInt("booking_id"));
		 booking.setUserId(rs.getInt("user_id"));
		 booking.setFlightId(rs.getInt("flight_id"));
		 booking.setBookingDate(rs.getTimestamp("booking_date"));
		 booking.setStatus(rs.getString("status"));
		 booking.setTicketNumber(rs.getString("ticket_number"));
		 booking.setCreatedAt(rs.getTimestamp("created_at"));
		 booking.setFlightNumber(rs.getString("flight_number"));
		 booking.setOrigin(rs.getString("origin"));
		 booking.setDestination(rs.getString("destination"));
		 booking.setDepartureTime(rs.getTimestamp("departure_time"));
		 booking.setArrivalTime(rs.getTimestamp("arrival_time"));
		return booking;
	}
}
