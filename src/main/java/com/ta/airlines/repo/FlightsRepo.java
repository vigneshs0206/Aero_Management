package com.ta.airlines.repo;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ta.airlines.dao.FlightsDAO;
import com.ta.airlines.model.Flights;
import com.ta.airlines.model.FlightsMapper;
import com.ta.airlines.utility.SqlQueries;

@Repository
public class FlightsRepo implements FlightsDAO {

	JdbcTemplate jdbcTemplate;

	@Autowired
	private SqlQueries sqlQueries;

	@Autowired
	public FlightsRepo(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

  
	@SuppressWarnings("deprecation")
	@Override
	public Flights getFlightById(Long id) {
		return jdbcTemplate.queryForObject(sqlQueries.SQL_GET_FLIGHTBYID, new Object[] { id }, new FlightsMapper());
	}

	@SuppressWarnings("deprecation")
	public Flights getFlightByNumber(String flight_number) {
		try {
			return jdbcTemplate.queryForObject(sqlQueries.SQL_FIND_FLIGHT_NUMBER, new Object[] { flight_number },
					new FlightsMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Flights> getAllFlights() {
		return jdbcTemplate.query(sqlQueries.SQL_GET_FLIGHTS, new FlightsMapper());
	}

	@Override
	public boolean deleteFlight(int id) {
		return jdbcTemplate.update(sqlQueries.SQL_DELETE_FLIGHT, id) > 0;
	}

	@Override
	public boolean updateFlight(int id, Flights flight) {
		return jdbcTemplate.update(sqlQueries.SQL_FLIGHT_UPDATE, flight.getFlightNumber(), flight.getOrigin(),
				flight.getDestination(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getSeatsAvailable(),
				flight.getPrice(), id) > 0;
	}

	@Override
	public boolean createFlight(Flights flight) {
		return jdbcTemplate.update(sqlQueries.SQL_CREATE_FLIGHT, flight.getFlightNumber(), flight.getOrigin(),
				flight.getDestination(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getSeatsAvailable(),
				flight.getPrice()) > 0;
	}

}
