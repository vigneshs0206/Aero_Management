package com.ta.airlines.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UsersMapper implements RowMapper<Users> {

	public Users mapRow(ResultSet rs, int i) throws SQLException {
		Users user = new Users();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setRole(rs.getString("role"));
		user.setCreateAt(rs.getTimestamp("create_at"));
		return user;
	}
}
