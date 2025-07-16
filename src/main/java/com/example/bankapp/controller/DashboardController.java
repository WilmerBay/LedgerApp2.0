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

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        // Optional: pick a default account (first one)
        Account account = user.getAccounts().isEmpty() ? null : user.getAccounts().get(0);
        model.addAttribute("account", account);

        return "dashboard";
    }

}
