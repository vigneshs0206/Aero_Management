package com.ta.airlines.repo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ta.airlines.dao.BookingsDAO;
import com.ta.airlines.model.Bookings;
import com.ta.airlines.model.BookingsMapper;
import com.ta.airlines.utility.SqlQueries;

@Repository
public class BookingsRepo implements BookingsDAO {

	JdbcTemplate jdbcTemplate;

	@Autowired
	private SqlQueries sqlQueries;

	@Autowired
	public BookingsRepo(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Bookings> getBookingByUserId(Long id) {
		try {
			@SuppressWarnings("deprecation")
			List<Bookings> list = jdbcTemplate.query(sqlQueries.SQL_GET_BOOKINGBYID, new Object[] { id },
					new BookingsMapper());
			System.out.println(list);
			return list;
		} catch (Exception e) {
			System.out.println("get booking id" + e);
			return null;
		}
	}

	@Override
	public Bookings createBooking(Bookings bookings) {
		try {
			Map<String, Object> result = jdbcTemplate.queryForMap(sqlQueries.SQL_CREATE_BOOKING, bookings.getUserId(),
					bookings.getFlightId(), bookings.getBookingDate(), "CONFIRMED");

			return new Bookings((Integer) result.get("id"), bookings.getUserId(), bookings.getFlightId(),
					bookings.getBookingDate(), "CONFIRMED", (String) result.get("ticket_number"),
					new Timestamp(System.currentTimeMillis()));

		} catch (Exception e) {
			System.out.println("create booking" + e);
			return null;
		}
	}

	@Override
	public Map<String, Object> getTicketDetails(String ticketNumber, int userId) {
		try {
			Map<String, Object> bookingDetails = jdbcTemplate.queryForMap(sqlQueries.SQL_GET_TICKET_DETAILS,
					ticketNumber, userId);
			System.out.println("bookingDetails" + bookingDetails);
			return bookingDetails;
		} catch (Exception e) {
			System.out.println("bookingDetails exep" + e);
			return null;
		}
	}

}
