package hac.service;

import hac.model.User;

import java.util.List;

public interface UserService {
	List<User> getAllUsers();
	
	User saveUsers(User user);
	
	User getUserById(Long id);

	User findByEmail(String email);
	
	User updateUser(User user);
	
	void deleteUserById(Long id);
}
