package com.example.bankapp.controller;

import com.example.bankapp.enums.AccountType;
import com.example.bankapp.model.Account;
import com.example.bankapp.model.Transaction;
import com.example.bankapp.model.User;
import com.example.bankapp.repository.TransactionRepository;
import com.example.bankapp.service.AccountService;
import com.example.bankapp.service.TransactionService;
import com.example.bankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        Account checking = user.getAccounts().stream()
                .filter(a -> a.getAccountType() == AccountType.CHECKING)
                .findFirst().orElse(null);

        Account savings = user.getAccounts().stream()
                .filter(a -> a.getAccountType() == AccountType.SAVINGS)
                .findFirst().orElse(null);

        // Fetch user accounts
        List<Account> accounts = accountService.getAccountsForUser(user);

        // Fetch transactions across all accounts
        List<Transaction> transactions = transactionRepository.findByAccountIn(accounts);

        /// Prepare chart data: convert transactions to JavaScript-friendly format
        List<String> labels = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();

        for (Transaction t : transactions) {
            labels.add("\"" + t.getTimestamp().toLocalDate().toString() + "\""); // Just date
            values.add(t.getAmount());
        }


        // Prepare list of other users for transfer dropdown
        List<User> otherUsers = userService.findAllExcept(user);

        // Set model attributes
        model.addAttribute("user", user);
        model.addAttribute("checking", checking);
        model.addAttribute("savings", savings);
        model.addAttribute("accounts", accounts);
        model.addAttribute("otherUsers", otherUsers);
        model.addAttribute("chartLabels", labels);
        model.addAttribute("chartValues", values);

        return "dashboard";
    }



}
