package com.ta.airlines.dao;

import java.util.List;
import java.util.Map;

import com.ta.airlines.model.Bookings;

public interface BookingsDAO {
	List<Bookings> getBookingByUserId(Long id);

	Bookings createBooking(Bookings bookings);

	Map<String, Object> getTicketDetails( String ticketNumber, int id);
}
