package hac.controllers;

import hac.model.User;
import hac.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/users")
	public String listUsers(Model model, HttpSession session) {
		// Check if user is authenticated
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			// User is not authenticated, redirect to sign-in page
			return "redirect:/signin";
		}
		User user = userService.getUserById(userId);
		if(!user.isAdmin()) {
			return "redirect:/users/page/" + user.getId();
		}

		// User is authenticated, proceed with fetching users
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}

	@GetMapping("/users/new")
	public String createUserForm(Model model, HttpSession session) {
		// Check if user is authenticated
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			// User is not authenticated, redirect to sign-in page
			return "redirect:/signin";
		}

		// User is authenticated, proceed with creating user form
		User user = new User();
		model.addAttribute("user", user);
		return "create_user";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// Clear the user ID from the session to perform logout
		session.removeAttribute("userId");
		return "redirect:/signin";
	}

	@PostMapping("/users")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		user.setAdmin(false);
		user.setActive(true);
		userService.saveUsers(user);
		return "redirect:/users?success";
	}

	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable Long id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "edit_user";
	}

	@PostMapping("/users/{id}")
	public String updateUser(@PathVariable Long id,
							 @ModelAttribute("user") User user,
							 Model model, HttpSession session) {

		// get user from database by id
		User existingUser = userService.getUserById(id);
		existingUser.setId(id);
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		// save updated user object
		userService.updateUser(existingUser);

		Long userId = (Long) session.getAttribute("userId");

		if (userId == null) {
			// User is not authenticated, redirect to sign-in page
			return "redirect:/signin";
		}

		if(!userService.getUserById(userId).isAdmin()) {
			return "redirect:/users/page/" + existingUser.getId();
		}

		return "redirect:/users";
	}

	// handler method to handle delete user request

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return "redirect:/users";
	}

	@GetMapping("/users/enable/{id}")
	public String enableUser(@PathVariable Long id) {
		User user = userService.getUserById(id);
		user.setActive(true);
		userService.updateUser(user);
		return "redirect:/users";
	}

	@GetMapping("/users/disable/{id}")
	public String disableUser(@PathVariable Long id) {
		User user = userService.getUserById(id);
		user.setActive(false);
		userService.updateUser(user);
		return "redirect:/users";
	}

	@GetMapping("/users/page/{id}")
	public String getUserPage(Model model, @PathVariable Long id) {
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("current", userService.getUserById(id));
		return "userpage";
	}

}
