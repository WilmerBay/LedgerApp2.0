package com.example.bankapp.controller;

import com.example.bankapp.model.Account;
import com.example.bankapp.model.User;
import com.example.bankapp.service.AccountService;
import com.example.bankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId,
                          @RequestParam BigDecimal amount,
                          Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Account account = accountService.getUserAccountById(user, accountId);

        accountService.deposit(account, amount);
        return "redirect:/dashboard";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId,
                           @RequestParam BigDecimal amount,
                           Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Account account = accountService.getUserAccountById(user, accountId);

        try {
            accountService.withdraw(account, amount);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "dashboard";
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromAccountId,
                           @RequestParam String toUsername,
                           @RequestParam BigDecimal amount,
                           Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Account fromAccount = accountService.getUserAccountById(user, fromAccountId);

        try {
            accountService.transferAmount(fromAccount, toUsername, amount);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "dashboard";
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/transactions")
    public String transactionHistory(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.findAccountByUsername(username);
        model.addAttribute("transactions", accountService.getTransactionHistory(account));
        return "transactions";
    }
}
