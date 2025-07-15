package com.example.bankapp.model;

import com.example.bankapp.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String username;
//    private String password;
    private BigDecimal balance;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
