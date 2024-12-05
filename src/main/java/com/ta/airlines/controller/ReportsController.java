package com.ta.airlines.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ta.airlines.model.ApiResponse;
import com.ta.airlines.repo.ReportsRepo;

@RestController
public class ReportsController {

	@Autowired
	private ReportsRepo reportsRepo;
	
	@GetMapping("/booking-history")
	public ResponseEntity<ApiResponse> getAllBookingHistory() {
		try {
			List<Map<String, Object>> bookingRecords = reportsRepo.getReports();
			for (Map<String,	 Object> row : bookingRecords) {
			    System.out.println(row);
			}
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