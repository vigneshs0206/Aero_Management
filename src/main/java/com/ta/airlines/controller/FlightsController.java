package com.ta.airlines.controller;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ta.airlines.model.ApiResponse;

import com.ta.airlines.model.Flights;

//import com.ta.airlines.service.DbService;

@RestController
public class FlightsController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
//	private DbService dbService;

	@GetMapping("/flights")
	public ResponseEntity<ApiResponse> getFlights() {
		try {
			String query = "SELECT * FROM aero_manage.flights";

			List<Flights> allFlights = jdbcTemplate.query(query, (rs, rowNum) -> {
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
			});

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
	public ResponseEntity<ApiResponse> getFlightById(@PathVariable("id") int id) {
		try {
			String query = "SELECT * FROM aero_manage.flights WHERE id = ?";

			@SuppressWarnings("deprecation")
			Flights flight = jdbcTemplate.queryForObject(query, new Object[] { id }, (rs, rowNum) -> {
				Flights f = new Flights();
				f.setId(rs.getInt("id"));
				f.setFlightNumber(rs.getString("flight_number"));
				f.setOrigin(rs.getString("origin"));
				f.setDestination(rs.getString("destination"));
				f.setDepartureTime(rs.getTimestamp("departure_time"));
				f.setArrivalTime(rs.getTimestamp("arrival_time"));
				f.setSeatsAvailable(rs.getInt("seats_available"));
				f.setPrice(rs.getBigDecimal("price"));
				f.setCreatedAt(rs.getTimestamp("created_at"));
				return f;
			});

			if (flight == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Flight not found", 404, null));
			}

			return ResponseEntity.ok(new ApiResponse("Flight retrieved successfully", 200, flight));

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
				String checkEmailQuery = "SELECT COUNT(*) FROM aero_manage.flights WHERE flight_number = ?";
				@SuppressWarnings("deprecation")
				int flightCount = jdbcTemplate.queryForObject(checkEmailQuery,
						new Object[] { flight.getFlightNumber() }, Integer.class);
				System.out.println("fligh count" + flightCount);
				if (flightCount > 0) {
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(new ApiResponse("Flight is Already Available...", 409, null));
				}
			} catch (Exception e) {
				System.out.println("flight add exception" + e);
			}

			String query = "INSERT INTO aero_manage.flights (flight_number, origin, destination, departure_time, arrival_time, seats_available, price) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			int rowsAffected = jdbcTemplate.update(query, flight.getFlightNumber(), flight.getOrigin(),
					flight.getDestination(), flight.getDepartureTime(), flight.getArrivalTime(),
					flight.getSeatsAvailable(), flight.getPrice());

			if (rowsAffected > 0) {
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
			String query = "UPDATE aero_manage.flights SET flight_number = ?, origin = ?, destination = ?, departure_time = ?, arrival_time = ?, "
					+ "seats_available = ?, price = ? WHERE id = ?";

			int rowsAffected = jdbcTemplate.update(query, flight.getFlightNumber(), flight.getOrigin(),
					flight.getDestination(), flight.getDepartureTime(), flight.getArrivalTime(),
					flight.getSeatsAvailable(), flight.getPrice(), id);

			if (rowsAffected > 0) {
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
			String query = "DELETE FROM aero_manage.flights WHERE id = ?";

			int rowsAffected = jdbcTemplate.update(query, id);

			if (rowsAffected > 0) {
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
