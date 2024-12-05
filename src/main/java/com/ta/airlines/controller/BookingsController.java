package com.ta.airlines.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.ta.airlines.model.ApiResponse;
import com.ta.airlines.model.Bookings;
import com.ta.airlines.repo.BookingsRepo;
import com.ta.airlines.utility.GeneralMethods;

@RestController
public class BookingsController {

	@Autowired
	private GeneralMethods generalMethods;

	@Autowired
	private BookingsRepo bookingsRepo;

	@PostMapping("/bookings")
	public ResponseEntity<ApiResponse> createBooking(@RequestBody Bookings newBookingRequest) {
		try {
			Bookings bookingCreatedResponse = bookingsRepo.createBooking(newBookingRequest);
			if (bookingCreatedResponse == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("Booking creation failed", 400, null));
			}
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse("Booking created successfully", 201, bookingCreatedResponse));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while creating the booking", 500, null));
		}
	}

	@GetMapping("/bookings/{userId}")
	public ResponseEntity<ApiResponse> getBookingHistory(@PathVariable Long userId) {
		try {
			List<Bookings> bookingHistory = bookingsRepo.getBookingByUserId(userId);
			if (bookingHistory == null) {
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
			Map<String, Object> bookingDetails = bookingsRepo.getTicketDetails(ticketNumber, userId);
			if (bookingDetails == null || bookingDetails.isEmpty()) {
				return ResponseEntity.status(404).body(null);
			} else {
				byte[] pdfContent = generalMethods.generateTicketPdf(bookingDetails, userId);
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket-" + ticketNumber + ".pdf")
						.contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfContent);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(null);
		}
	}

}
