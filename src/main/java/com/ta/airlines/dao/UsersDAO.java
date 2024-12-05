package com.ta.airlines.dao;
import java.util.List;
import com.ta.airlines.model.Users;

public interface UsersDAO {
	Users getUserById(Long id);
	
	Users getUserByEmail(String email);

	List<Users> getAllUsers();

	boolean deleteUser(Users user);

	boolean updateUser(Users user);

	boolean createUser(Users user);
	
	
}
