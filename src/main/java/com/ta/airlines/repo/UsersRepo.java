package com.ta.airlines.repo;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ta.airlines.dao.UsersDAO;
import com.ta.airlines.model.Users;
import com.ta.airlines.model.UsersMapper;

@Repository
public class UsersRepo implements UsersDAO {
	JdbcTemplate jdbcTemplate;
	private final String SQL_GET_USERS = "SELECT * FROM aero_manage.users";
	private final String SQL_INSERT_USERS = "INSERT INTO aero_manage.Users (email, password, role, create_at) VALUES (?, ?, ?, ?)";
	private final String SQL_FIND_USERS_MAIL = "SELECT * FROM aero_manage.Users WHERE email = ?";
	private final String SQL_FIND_USERS_ID = "SELECT * FROM aero_manage.Users WHERE id = ?";
//	private final String SQL_UPDATE_USERS = "SELECT * FROM aero_manage.Users WHERE id = ?";

	@Autowired
	public UsersRepo(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Users getUserById(Long id) {
		return jdbcTemplate.queryForObject(SQL_FIND_USERS_ID, new Object[] { id }, new UsersMapper());
	}

	@SuppressWarnings("deprecation")
	@Override
	public Users getUserByEmail(String email) {
		try {
			return jdbcTemplate.queryForObject(SQL_FIND_USERS_MAIL, new Object[] { email }, new UsersMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

//	@Override
//	public boolean getUserByEmail(String email) {
//		
//		return jdbcTemplate.update(SQL_FIND_USERS_MAIL, email) > 0;	
//	}

	@Override
	public List<Users> getAllUsers() {
		return jdbcTemplate.query(SQL_GET_USERS, new UsersMapper());
	}

	@Override
	public boolean createUser(Users user) {
		return jdbcTemplate.update(SQL_INSERT_USERS, user.getEmail(), user.getPassword(), user.getRole(),
				user.getCreateAt()) > 0;
	}

}
