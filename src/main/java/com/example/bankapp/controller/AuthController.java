package com.example.bankapp.controller;

import com.example.bankapp.model.User;
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
                                  @RequestParam String password,
                                  Model model) {
        try {
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .build();

            userService.save(user);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

}
