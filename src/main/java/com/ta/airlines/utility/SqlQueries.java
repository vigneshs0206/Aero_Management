package com.ta.airlines.utility;

import org.springframework.stereotype.Component;

@Component
public class SqlQueries {

	public final String SQL_GET_FLIGHTS = "SELECT * FROM aero_manage.flights";
	public final String SQL_GET_FLIGHTBYID = "SELECT * FROM aero_manage.flights WHERE id = ?";
	public final String SQL_CREATE_FLIGHT = "INSERT INTO aero_manage.flights (flight_number, origin, destination, departure_time, arrival_time, seats_available, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public final String SQL_FIND_FLIGHT_NUMBER = "SELECT * FROM aero_manage.flights WHERE flight_number = ?";
	public final String SQL_FLIGHT_UPDATE = "UPDATE aero_manage.flights SET flight_number = ?, origin = ?, destination = ?, departure_time = ?, arrival_time = ? , seats_available = ?, price = ? WHERE id = ?";
	public final String SQL_DELETE_FLIGHT = "DELETE FROM aero_manage.flights WHERE id = ?";
	public final String SQL_CREATE_BOOKING = "INSERT INTO aero_manage.bookings (user_id, flight_id, booking_date, status, ticket_number, created_at) VALUES (?, ?, ?, ?, uuid_generate_v4(), CURRENT_TIMESTAMP) RETURNING id, ticket_number";
	public final String SQL_GET_BOOKINGBYID = "SELECT b.id as booking_id, b.user_id, b.flight_id, b.booking_date, b.status, b.ticket_number, b.created_at,f.flight_number, f.origin, f.destination, f.departure_time, f.arrival_time FROM aero_manage.bookings b JOIN aero_manage.flights f ON b.flight_id = f.id WHERE b.user_id = ? ORDER BY b.created_at DESC";

	public final String SQL_GET_TICKET_DETAILS = "SELECT b.id, b.booking_date, b.status, b.ticket_number, f.flight_number, f.origin, f.destination, f.departure_time, f.arrival_time FROM aero_manage.bookings b JOIN aero_manage.flights f ON b.flight_id = f.id WHERE b.ticket_number = ? AND b.user_id = ?";

	public final String SQL_GET_TICKET_REPORTS = "SELECT b.id AS booking_id, b.user_id, u.email AS user_email, b.flight_id, f.flight_number, b.booking_date, b.status, b.ticket_number, b.created_at FROM aero_manage.bookings b JOIN aero_manage.users u ON b.user_id = u.id JOIN aero_manage.flights f ON b.flight_id = f.id";
}
