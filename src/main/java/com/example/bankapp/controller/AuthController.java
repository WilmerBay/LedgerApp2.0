package com.example.bankapp.controller;

import com.example.bankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  Model model) {

        System.out.println("Register request received: " + username + ", " + email);

        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "Username already taken");
            return "register";
        }

        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }

        try {
            userService.createUser(username, email, password);
            System.out.println("User registered successfully");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            System.err.println("Registration error: " + e.getMessage());
            return "register";
        }
    }



}
