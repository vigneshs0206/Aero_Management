package com.ta.airlines.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ta.airlines.dto.LoginRequest;
import com.ta.airlines.entity.QueryResult;
import com.ta.airlines.model.Users;
import com.ta.airlines.repo.UsersRepo;
import com.ta.airlines.service.DbService;
import com.ta.airlines.model.ApiResponse;

@RestController
public class UsersController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DbService dbService;

	@Autowired
	private UsersRepo usersRepo;

	@GetMapping("/test")
	public String testApi() {
		return "sdf";
	}

	@GetMapping("/userstest")
	public ResponseEntity<ApiResponse> testApi2() {
		List<Users> allUsers = new ArrayList<>();
		QueryResult queryResult = null;

		try {
			String query = "SELECT * FROM aero_manage.Users";

			queryResult = dbService.executeQueryforRs(query);

			ResultSet rs = queryResult.getResultSet();

			if (rs == null || !rs.isBeforeFirst()) {
				return ResponseEntity.ok(new ApiResponse("No users found", 204, null));
			}

			while (rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				user.setCreateAt(rs.getTimestamp("create_at"));
				allUsers.add(user);
			}

		} catch (Exception e) {
			System.out.println("Error retrieving users: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while retrieving users", 500, null));
		} finally {
			if (queryResult != null) {
				try {
					if (queryResult.getResultSet() != null)
						queryResult.getResultSet().close();
					if (queryResult.getStatement() != null)
						queryResult.getStatement().close();
					if (queryResult.getConnection() != null)
						queryResult.getConnection().close();
					System.out.println("Db connection is closed successfully");
				} catch (Exception e) {
					System.out.println("Error closing database resources: " + e.getMessage());
				}
			}
		}

		return ResponseEntity.ok(new ApiResponse("Users retrieved successfully", 200, allUsers));
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse> getUsers() {
		try {
			List<Users> allUsers = usersRepo.getAllUsers();
			System.out.println("allUsers" + allUsers);
			if (allUsers.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("No users found", 204, null));
			}
			return ResponseEntity.ok(new ApiResponse("Users retrieved successfully", 200, allUsers));
		} catch (Exception e) {
			System.out.println("Error retrieving users: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while retrieving users", 500, null));
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") Long id) {
		try {
			Users user = usersRepo.getUserById(id);
			if (user != null) {
				return ResponseEntity.ok(new ApiResponse("User retrieved successfully", 200, user));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", 404, null));
			}

		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", 404, null));
		} catch (Exception e) {
			System.out.println("Error retrieving user: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while retrieving the user", 500, null));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
		try {
			Users emailUser = new Users();
			try {
				String newEmail = loginRequest.getEmail();
				emailUser = usersRepo.getUserByEmail(newEmail);
				if (emailUser == null) {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
							.body(new ApiResponse("User Not Found...", 409, null));
				}
			} catch (EmptyResultDataAccessException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ApiResponse("User Not Found...", 401, null));
			}

			if (emailUser != null && emailUser.getPassword().equals(loginRequest.getPassword())) {
				String mockJwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InZpZ25lc2hAZW1haWwuY29tIiwiaWQiOjV9.mGGrZqODB3_D83BCz08PhF3JqF_-IOqz1_t2IGZjNNE";
				return ResponseEntity.ok(new ApiResponse("Login successful", 200, mockJwtToken));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ApiResponse("Invalid username or password", 401, null));
			}

		} catch (Exception e) {
			System.out.println("Error logging in: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred during login", 500, null));
		}
	}

	@PostMapping("/users/register")
	public ResponseEntity<ApiResponse> addUser(@RequestBody Users newUser) {
		try {
			try {
				String newEmail = newUser.getEmail();
				Users emailUser = usersRepo.getUserByEmail(newEmail);
				if (emailUser != null) {
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(new ApiResponse("Email is already in use", 409, null));
				}
			} catch (Exception e) {
				System.out.println("Exception in email creation" + e);
			}
			boolean inserted = usersRepo.createUser(newUser);
			if (inserted) {
				return ResponseEntity.ok(new ApiResponse("User Created successfully", 201, null));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse("Failed to add user", 500, null));
			}
		} catch (Exception e) {
			System.out.println("Error adding user: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An error occurred while adding the user", 500, null));
		}
	}

	public static void main(String[] args) {
	}
}
