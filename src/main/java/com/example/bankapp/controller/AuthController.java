package com.example.bankapp.controller;

import com.example.bankapp.enums.AccountType;
import com.example.bankapp.model.Account;
import com.example.bankapp.model.User;
import com.example.bankapp.service.AccountService;
import com.example.bankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

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
                    .password(passwordEncoder.encode(password))
                    .build();

            User savedUser = userService.save(user);

            // Create checking and savings accounts
            Account checking = Account.builder()
                    .accountType(AccountType.CHECKING)
                    .balance(BigDecimal.ZERO)
                    .user(savedUser)
                    .build();

            Account savings = Account.builder()
                    .accountType(AccountType.SAVINGS)
                    .balance(BigDecimal.ZERO)
                    .user(savedUser)
                    .build();

            accountService.save(checking);
            accountService.save(savings);

            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }


}
