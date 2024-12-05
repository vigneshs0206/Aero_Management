package com.ta.airlines.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ta.airlines.model.ApiResponse;

@RestController
public class ReportsController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/booking-history")
	public ResponseEntity<ApiResponse> getAllBookingHistory() {
		try {
//            String query = "SELECT b.id AS booking_id, b.user_id, u.name AS user_name, " +
//                           "b.flight_id, f.flight_number, b.booking_date, b.status, " +
//                           "b.ticket_number, b.created_at " +
//                           "FROM aero_manage.bookings b " +
//                           "JOIN aero_manage.users u ON b.user_id = u.id " +
//                           "JOIN aero_manage.flights f ON b.flight_id = f.id";

			String query = "SELECT b.id AS booking_id, b.user_id, u.email AS user_email,  b.flight_id, f.flight_number, b.booking_date, b.status, b.ticket_number, b.created_at\r\n"
					+ "FROM aero_manage.bookings b JOIN aero_manage.users u ON b.user_id = u.id \r\n"
					+ "                           JOIN aero_manage.flights f ON b.flight_id = f.id\r\n" + "";

			List<Map<String, Object>> bookingRecords = jdbcTemplate.queryForList(query);

			if (bookingRecords.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No booking records found", 204, null));
			}

			return ResponseEntity.ok(new ApiResponse("Booking history retrieved successfully", 200, bookingRecords));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500)
					.body(new ApiResponse("An error occurred while retrieving booking history", 500, null));
		}
	}
}