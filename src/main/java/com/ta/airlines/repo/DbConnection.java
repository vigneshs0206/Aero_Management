package com.ta.airlines.repo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DbConnection {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Connection getConnection() {
		Connection connection = null;
		try {
			System.out.println(jdbcTemplate);
			connection = jdbcTemplate.getDataSource().getConnection();
			System.out.println("Success");
		} catch (SQLException e) {
			System.out.println(e);
//			log.error("SQLException - Unable to connect the database : "+e.getMessage());

		} catch (Exception e) {
			System.out.println(e);
//			log.error("Exception - Unable to connect the database : "+e.getMessage());
		}
		return connection;
	}

	public boolean closeConnection(Connection connect, ResultSet resultSet, CallableStatement calStatement)
			throws SQLException {
		if (connect != null) {
			connect.close();
			if (resultSet != null) {
				resultSet.close();
			}
			if (calStatement != null) {
				calStatement.close();
			}
			return true;
		}
		return false;

	}

}
