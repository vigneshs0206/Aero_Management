package com.ta.airlines.repo;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ta.airlines.dao.ReportsDAO;
import com.ta.airlines.utility.SqlQueries;

@Repository
public class ReportsRepo implements ReportsDAO {

	JdbcTemplate jdbcTemplate;

	@Autowired
	private SqlQueries sqlQueries;

	@Autowired
	public ReportsRepo(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Map<String, Object>> getReports() {
		try {
			return jdbcTemplate.queryForList(sqlQueries.SQL_GET_TICKET_REPORTS);
		} catch (Exception e) {
			return null;
		}
	}

}
