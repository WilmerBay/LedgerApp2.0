package com.example.bankapp.service;

import com.example.bankapp.enums.AccountType;
import com.example.bankapp.model.Account;
import com.example.bankapp.model.User;
import com.example.bankapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, AccountService accountService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String email, String password) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        // First save the user
        User savedUser = userRepository.save(user);

        // Then create and save the accounts referencing the saved user
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

        return savedUser;
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllExcept(User currentUser) {
        return userRepository.findAll()
                .stream()
                .filter(u -> !u.getId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
