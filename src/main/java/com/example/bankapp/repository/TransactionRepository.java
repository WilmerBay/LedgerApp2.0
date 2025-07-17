package com.example.bankapp.repository;

import com.example.bankapp.model.Account;
import com.example.bankapp.model.Transaction;
import com.example.bankapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByAccountOrderByTimestamp(Account account);
    List<Transaction> findByAccountIn(List<Account> accounts);

}

