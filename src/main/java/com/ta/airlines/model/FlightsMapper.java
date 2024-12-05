package com.ta.airlines.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FlightsMapper implements RowMapper<Flights> {
	public Flights mapRow(ResultSet rs, int i) throws SQLException {
		Flights flight = new Flights();
		flight.setId(rs.getInt("id"));
		flight.setFlightNumber(rs.getString("flight_number"));
		flight.setOrigin(rs.getString("origin"));
		flight.setDestination(rs.getString("destination"));
		flight.setDepartureTime(rs.getTimestamp("departure_time"));
		flight.setArrivalTime(rs.getTimestamp("arrival_time"));
		flight.setSeatsAvailable(rs.getInt("seats_available"));
		flight.setPrice(rs.getBigDecimal("price"));
		flight.setCreatedAt(rs.getTimestamp("created_at"));
		return flight;
	}
}
