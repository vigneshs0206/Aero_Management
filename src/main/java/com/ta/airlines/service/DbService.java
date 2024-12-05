package com.ta.airlines.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ta.airlines.entity.QueryResult;
import com.ta.airlines.repo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

//class QueryResult {
//    private Connection connection;
//    private Statement statement;
//    private ResultSet resultSet;
//
//    public QueryResult(Connection connection, Statement statement, ResultSet resultSet) {
//        this.connection = connection;
//        this.statement = statement;
//        this.resultSet = resultSet;
//    }
//
//    public Connection getConnection() {
//        return connection;
//    }
//
//    public Statement getStatement() {
//        return statement;
//    }
//
//    public ResultSet getResultSet() {
//        return resultSet;
//    }
//}

@Service
public class DbService {

	
	@Autowired
	DbConnection dbconnection;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Object db_operation(String procedure_name, Object request1) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		CallableStatement callableStatement = null;

		connection = dbconnection.getConnection();
		try {

			Object response = new Object();
			callableStatement = connection.prepareCall("{call " + procedure_name + "(?)}");
			// callableStatement = connection.prepareCall("{call " + procedure_name + "}");

			String json = new ObjectMapper().writeValueAsString(request1);

			// callableStatement.setString(1, request1.toString());
			callableStatement.setString(1, json.toString());
			// callableStatement.setString(1, request1.toString());

			rs = callableStatement.executeQuery();
			System.out.println("Query executed successfully");

			while (rs.next()) {
				response = rs.getObject("responseJson");
				System.out.println("response received");
				return response;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				dbconnection.closeConnection(connection, rs, callableStatement);
			} catch (Exception e) {
				System.out.println("database connection gets closed");
			}
		}

		return null;
	}

	public List<Object> executeQuery(String query) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		Statement statement = null;
		List<Object> responseList = new ArrayList<>();

		connection = dbconnection.getConnection();
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			System.out.println("Query executed successfully");

			// Iterate over the result set and add the results to the responseList
			while (rs.next()) {
				System.out.println(rs);
				// Assuming the query returns rows with a column named "responseJson"
//				Object response = rs.getObject("responseJson");
//				responseList.add();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				dbconnection.closeConnection(connection, rs, (CallableStatement) statement);
			} catch (Exception e) {
				System.out.println("Database connection gets closed");
			}
		}

		return responseList;
	}

	public QueryResult executeQueryforRs(String query) throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			// Get database connection
			connection = dbconnection.getConnection();

			// Create a statement
			statement = connection.createStatement();

			// Execute the query and return the result set
			rs = statement.executeQuery(query);

			System.out.println("Query executed successfully");
			return new QueryResult(connection, statement, rs);

		} catch (Exception e) {
			throw new Exception("Error while executing query: " + e.getMessage(), e);
		}
	}

	

	public int executeUpdate(String query, Object... params) {		
		return jdbcTemplate.update(query, params);
	}
}
