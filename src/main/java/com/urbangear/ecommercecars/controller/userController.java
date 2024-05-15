package com.urbangear.ecommercecars.controller;

import com.urbangear.ecommercecars.domain.user;
import com.urbangear.ecommercecars.service.userService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Optional<user> user = userService.login(username, password);
        if (user.isPresent()) {
            // Set the user session
            session.setAttribute("session", user.get());

            // Set the username in the session
            session.setAttribute("username", username);

            if (username.equals("admin") && password.equals("12345")) {
                return "admin_welcome";
            } else {
                return "redirect:/web/home";
            }
        } else {
            // Handle login failure, you can redirect to a login page or return an error message
//            model.addAttribute("error", "Invalid credentials");
            return "redirect:/api/users/error";
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String first_name, @RequestParam String last_name,
                           @RequestParam String username, @RequestParam String your_email,
                           @RequestParam String password, @RequestParam String confirm_password) {
        // Check if passwords match
        if (!password.equals(confirm_password)) {
            // Handle password mismatch, you may want to add an error message
            return "redirect:/registration?error=passwordMismatch";
        }

        user newUser = new user();
        newUser.setFirstName(first_name);
        newUser.setLastName(last_name);
        newUser.setUsername(username);
        newUser.setEmail(your_email);
        newUser.setPassword(password);

        userService.register(newUser);
        // Redirect to login page after successful registration
        return "redirect:/api/users/login";
    }


    @RequestMapping("/error")
    public String handleError() {
        // Provide a custom error page or redirect to a specific page
        return "loginErrorPage";
    }

    @RequestMapping("/success")
    public String handleSuccess() {
        // Provide a custom error page or redirect to a specific page
        return "successPage";
    }

}
