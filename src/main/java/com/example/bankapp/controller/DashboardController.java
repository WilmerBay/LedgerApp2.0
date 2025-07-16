package com.example.bankapp.controller;

import com.example.bankapp.enums.AccountType;
import com.example.bankapp.model.Account;
import com.example.bankapp.model.User;
import com.example.bankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        // Always try to show the CHECKING account first
        Account defaultAccount = user.getAccounts()
                .stream()
                .filter(acc -> acc.getAccountType() == AccountType.CHECKING)
                .findFirst()
                .orElse(user.getAccounts().stream().findFirst().orElse(null));

        model.addAttribute("user", user);
        model.addAttribute("accounts", user.getAccounts());
        model.addAttribute("account", defaultAccount);

        // For transfer dropdown
        List<User> otherUsers = userService.findAllExcept(user);
        model.addAttribute("otherUsers", otherUsers);

        return "dashboard";
    }

}
