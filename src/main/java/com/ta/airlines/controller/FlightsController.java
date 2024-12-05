package com.ta.airlines.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ta.airlines.model.ApiResponse;

import com.ta.airlines.model.Flights;
import com.ta.airlines.repo.FlightsRepo;

@RestController
public class FlightsController {

	@Autowired
	private FlightsRepo flightsRepo;

//	public FlightsController(FlightsRepo flightsRepo) {
//		this.flightsRepo = flightsRepo;
//	}

	@GetMapping("/flights")
	public ResponseEntity<ApiResponse> getFlights() {
		try {
			List<Flights> allFlights = flightsRepo.getAllFlights();
			if (allFlights.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No Flights found", 204, null));
			}
			return ResponseEntity.ok(new ApiResponse("Flights retrieved successfully", 200, allFlights));
		} catch (Exception e) {
			System.out.println("Error retrieving users: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while retrieving users", 500, null));
		}
	}

	@GetMapping("/flights/{id}")
	public ResponseEntity<ApiResponse> getFlightById(@PathVariable("id") Long id) {
		try {
			Flights flight = flightsRepo.getFlightById(id);
			if (flight != null) {
				return ResponseEntity.ok(new ApiResponse("Flight retrieved successfully", 200, flight));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Flight not found", 404, null));
			}
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Flight not found", 404, null));
		} catch (Exception e) {
			System.out.println("Error retrieving flight: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while retrieving the flight", 500, null));
		}
	}

	@PostMapping("/flights")
	public ResponseEntity<ApiResponse> createFlight(@RequestBody Flights flight) {
		try {
			try {
				Flights flights = flightsRepo.getFlightByNumber(flight.getFlightNumber());
				if (flights != null) {
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(new ApiResponse("Flight is Already Available...", 409, null));
				}
			} catch (Exception e) {
				System.out.println("flight add exception" + e);
			}
			boolean flightCreated = flightsRepo.createFlight(flight);
			if (flightCreated) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new ApiResponse("Flight created successfully", 201, flight));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ApiResponse("Failed to create flight", 400, null));
			}

		} catch (Exception e) {
			System.out.println("Error creating flight: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while creating the flight", 500, null));
		}
	}

	@PutMapping("/flights/{id}")
	public ResponseEntity<ApiResponse> updateFlight(@PathVariable("id") int id, @RequestBody Flights flight) {
		try {
			boolean flightUpdated = flightsRepo.updateFlight(id, flight);
			if (flightUpdated) {
				return ResponseEntity.ok(new ApiResponse("Flight updated successfully", 200, flight));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Flight not found", 404, null));
			}

		} catch (Exception e) {
			System.out.println("Error updating flight: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while updating the flight", 500, null));
		}
	}

	@DeleteMapping("/flights/{id}")
	public ResponseEntity<ApiResponse> deleteFlight(@PathVariable("id") int id) {
		try {
			boolean flightDeleted = flightsRepo.deleteFlight(id);
			if (flightDeleted) {
				return ResponseEntity.ok(new ApiResponse("Flight deleted successfully", 200, null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Flight not found", 404, null));
			}
		} catch (Exception e) {
			System.out.println("Error deleting flight: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while deleting the flight", 500, null));
		}
	}

	public static void main(String[] args) {

	}
}
