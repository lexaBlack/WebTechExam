package com.urbangear.ecommercecars.controller;

import com.urbangear.ecommercecars.domain.user;
import com.urbangear.ecommercecars.service.userService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.Properties;
import java.util.Random;

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

    @GetMapping("/forget-password")
    public String handleForgetPassword() {
        return "forget_password";
    }


    @PostMapping("/forget-password")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "redirect:/api/users/reset-password-fail";
        }

        Optional<user> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            user existingUser = optionalUser.get();
            existingUser.setPassword(newPassword); // Save the new password
            userService.register(existingUser); // Update the user in the database
            return "redirect:/api/users/reset-password-success";
        } else {
            model.addAttribute("error", "Username not found!");
            return "redirect:/api/users/reset-password-fail";
        }
    }

    @GetMapping("/reset-password-success")
    public String handleresetPassword() {
        return "reset_password_success";
    }

    @GetMapping("/reset-password-fail")
    public String handleresetPasswordfail() {
        return "reset_password_fail";
    }

    public String SendMail(String recipient, String messageBody) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        final String senderEmail = "kmlcharles@gmail.com";
        final String senderPassword = "ozvs rvkx oijg ngvr";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Two-Factor Authentication Code");

            // Create a MimeBodyPart to represent the message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageBody, "text/html");

            // Create a Multipart object to hold the body parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Set the content of the message to be the multipart object
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            return "redirect:/api/users/verify-2fa";

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/send2FO")
    public String sendmail2fo() {
        return "send2faEmail";
    }

    @GetMapping("/verify-2fa")
    public String entermail2fo() {
        return "2FO_Page";
    }

    @PostMapping("/send2FO")
    public String sendmail2fo(@RequestParam String email, HttpSession session) {
        // Generate a random 6-digit 2FA code
        String twoFactorCode = generate2FACode();

        // Log the code for debugging
        System.out.println("Generated 2FA Code: " + twoFactorCode);

        // Store the code in the session for later validation
        session.setAttribute("2FACode", twoFactorCode);

        // Create the email body with the 2FA code
        String message = "<h3>Your Two-Factor Authentication Code:</h3><p>" + twoFactorCode + "</p>";

        // Send the 2FA code to the user's email
        SendMail(email, message);

        // Redirect to a page where the user can enter the 2FA code
        return "redirect:/api/users/verify-2fa";
    }

    // Generate a random 6-digit 2FA code
    private String generate2FACode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a 6-digit number
        return String.valueOf(code);
    }

    @PostMapping("/verify-2fa")
    public String verify2FACode(@RequestParam String twoFACode, HttpSession session) {
        // Get the stored 2FA code from the session
        String storedCode = (String) session.getAttribute("2FACode");

        // Log the stored and entered codes for debugging
        System.out.println("Stored 2FA Code from Session: " + storedCode);
        System.out.println("Entered 2FA Code: " + twoFACode);

        // Check if the entered code matches the stored code
        if (storedCode != null && storedCode.equals(twoFACode)) {
            // If the codes match, redirect to the user's dashboard or home page
            System.out.println("2FA Code Verified Successfully");
            return "redirect:/api/users/forget-password"; // Change to your desired page
        } else {
            // If the codes do not match, return an error message
            System.out.println("2FA Code Mismatch");
            return "redirect:/api/users/invalid_2FA"; // You can create a page to show error message
        }
    }

}
