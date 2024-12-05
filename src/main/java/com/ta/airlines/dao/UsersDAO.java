package com.ta.airlines.dao;
import java.util.List;
import com.ta.airlines.model.Users;

public interface UsersDAO {
	Users getUserById(Long id);
	
	Users getUserByEmail(String email);

//	boolean getUserByEmail(String email);
	
	List<Users> getAllUsers();

	boolean createUser(Users user);
	
	
}
