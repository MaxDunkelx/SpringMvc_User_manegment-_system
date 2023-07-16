package hac.controllers;

import hac.model.Credentials;
import hac.model.User;
import hac.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping("/")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String getSignInPage(Model model) {
        Credentials cred = new Credentials();
        model.addAttribute("cred", cred);
        return "signin";
    }
    @GetMapping("/create_user") // New mapping for creating a new user
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create_user";
    }

    @PostMapping("/create_user") //  mapping for saving the new user
    public String saveNewUser(@ModelAttribute("user") User user,
                              BindingResult result,
                              Model model) {
        user.setAdmin(false);
        user.setActive(true);
        User exist = userService.findByEmail(user.getEmail());
		if(exist !=null){
			result.rejectValue("email", null, "There is already an account registered with that email");
		}
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "redirect:/create_user";
		}
        userService.saveUsers(user);
        return "redirect:/signin"; // Redirect to the signin page after creating the user
    }



    @PostMapping("/signin")
    public String login(@ModelAttribute("cred") Credentials cred, HttpSession session) {
        // Perform authentication logic
        User user = userService.findByEmail(cred.email);
        if (user != null && user.getPassword().equals(cred.password)) {
            // Authentication successful, store user ID in the session
            session.setAttribute("userId", user.getId());

            user.setTimestamp(Instant.now().toEpochMilli());
            userService.updateUser(user);

            if (user.isAdmin()) {
                return "redirect:/users";
            } else {
                return "redirect:/users/page/" + user.getId();
            }
        } else {
            // Authentication failed, handle accordingly
            return "redirect:/signin?error";
        }
    }
}
