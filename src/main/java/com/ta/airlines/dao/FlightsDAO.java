package com.ta.airlines.dao;

import java.util.List;

import com.ta.airlines.model.Flights;

public interface FlightsDAO {
	Flights getFlightById(Long id);

	List<Flights> getAllFlights();

	boolean deleteFlight(int id);

	boolean updateFlight(int id,Flights flight);

	boolean createFlight(Flights flight);
}
