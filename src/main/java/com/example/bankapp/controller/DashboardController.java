package com.example.bankapp.controller;

import com.example.bankapp.model.Account;
import com.example.bankapp.model.User;
import com.example.bankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("account", user.getAccounts().get(0)); // optional default

        // Load all other users and their accounts for transfer
        List<User> otherUsers = userService.findAllExcept(user);
        model.addAttribute("otherUsers", otherUsers);

        return "dashboard";
    }


}
