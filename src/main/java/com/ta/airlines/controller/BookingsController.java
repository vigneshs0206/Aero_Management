package com.ta.airlines.controller;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.ta.airlines.model.ApiResponse;
import com.ta.airlines.model.BookingHistoryResponse;
import com.ta.airlines.model.BookingRequest;
import com.ta.airlines.model.BookingResponse;

@RestController
public class BookingsController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PostMapping("/bookings")
	public ResponseEntity<ApiResponse> createBooking(@RequestBody BookingRequest bookingRequest) {
		try {
			String query = "INSERT INTO aero_manage.bookings (user_id, flight_id, booking_date, status, ticket_number, created_at) "
					+ "VALUES (?, ?, ?, ?, uuid_generate_v4(), CURRENT_TIMESTAMP) RETURNING id, ticket_number";

			Map<String, Object> result = jdbcTemplate.queryForMap(query, bookingRequest.getUserId(),
					bookingRequest.getFlightId(), bookingRequest.getBookingDate(), "CONFIRMED");
			System.out.println("RESULT MAP" + result);
			BookingResponse bookingResponse = new BookingResponse((Integer) result.get("id"),
					bookingRequest.getUserId(), bookingRequest.getFlightId(), bookingRequest.getBookingDate(),
					"CONFIRMED", (String) result.get("ticket_number"), new Timestamp(System.currentTimeMillis()));

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse("Booking created successfully", 201, bookingResponse));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while creating the booking", 500, null));
		}
	}

	@GetMapping("/bookings/{userId}")
	public ResponseEntity<ApiResponse> getBookingHistory(@PathVariable Integer userId) {
		try {
			String query = "SELECT b.id, b.user_id, b.flight_id, b.booking_date, b.status, b.ticket_number, b.created_at, "
					+ "f.flight_number, f.origin, f.destination, f.departure_time, f.arrival_time "
					+ "FROM aero_manage.bookings b " + "JOIN aero_manage.flights f ON b.flight_id = f.id "
					+ "WHERE b.user_id = ? " + "ORDER BY b.created_at DESC";

			@SuppressWarnings("deprecation")
			List<BookingHistoryResponse> bookingHistory = jdbcTemplate.query(query, new Object[] { userId },
					(rs, test) -> {
						BookingHistoryResponse history = new BookingHistoryResponse();
						history.setBookingId(rs.getInt("id"));
						history.setUserId(rs.getInt("user_id"));
						history.setFlightId(rs.getInt("flight_id"));
						history.setBookingDate(rs.getTimestamp("booking_date"));
						history.setStatus(rs.getString("status"));
						history.setTicketNumber(rs.getString("ticket_number"));
						history.setCreatedAt(rs.getTimestamp("created_at"));
						history.setFlightNumber(rs.getString("flight_number"));
						history.setOrigin(rs.getString("origin"));
						history.setDestination(rs.getString("destination"));
						history.setDepartureTime(rs.getTimestamp("departure_time"));
						history.setArrivalTime(rs.getTimestamp("arrival_time"));
						return history;
					});

			if (bookingHistory.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No bookings found for this user", 204, null));
			}

			return ResponseEntity.ok(new ApiResponse("Bookings retrieved successfully", 200, bookingHistory));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while retrieving bookings", 500, null));
		}
	}

	@GetMapping("/api/tickets/download")
	public ResponseEntity<byte[]> downloadTicket(@RequestParam("ticketNumber") String ticketNumber,
			@RequestParam("userId") Integer userId) {

		try {
			String query = "SELECT b.id, b.booking_date, b.status, b.ticket_number, f.flight_number, "
					+ "f.origin, f.destination, f.departure_time, f.arrival_time " + "FROM aero_manage.bookings b "
					+ "JOIN aero_manage.flights f ON b.flight_id = f.id "
					+ "WHERE b.ticket_number = ? AND b.user_id = ?";

			Map<String, Object> bookingDetails = jdbcTemplate.queryForMap(query, ticketNumber, userId);

			if (bookingDetails == null || bookingDetails.isEmpty()) {
				return ResponseEntity.status(404).body(null);
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PdfWriter writer = new PdfWriter(outputStream);
			PdfDocument pdfDocument = new PdfDocument(writer);
			Document document = new Document(pdfDocument);

			document.add(new Paragraph("Aeroplane Ticket Booking System").setBold());
			document.add(new Paragraph("Ticket Number: " + bookingDetails.get("ticket_number")));
			document.add(new Paragraph("User ID: " + userId));
			document.add(new Paragraph("Booking Date: " + bookingDetails.get("booking_date")));
			document.add(new Paragraph("Status: " + bookingDetails.get("status")));
			document.add(new Paragraph("Flight Details:"));
			document.add(new Paragraph("Flight Number: " + bookingDetails.get("flight_number")));
			document.add(new Paragraph("From: " + bookingDetails.get("origin")));
			document.add(new Paragraph("To: " + bookingDetails.get("destination")));
			document.add(new Paragraph("Departure Time: " + bookingDetails.get("departure_time")));
			document.add(new Paragraph("Arrival Time: " + bookingDetails.get("arrival_time")));
			document.add(new Paragraph("Generated At: " + new Timestamp(System.currentTimeMillis())));

			document.close();

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket-" + ticketNumber + ".pdf")
					.contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(outputStream.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(null);
		}
	}

}
