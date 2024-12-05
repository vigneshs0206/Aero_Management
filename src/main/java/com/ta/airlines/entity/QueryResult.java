package com.ta.airlines.entity;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryResult {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public QueryResult(Connection connection, Statement statement, ResultSet resultSet) {
        this.connection = connection;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    // Getters
    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}
