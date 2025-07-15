package com.example.bankapp.repository;

import com.example.bankapp.model.Account;
import com.example.bankapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(User user);
}
