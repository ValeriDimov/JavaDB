package com.example.spring_data_intro_demo.services;

import com.example.spring_data_intro_demo.entities.Account;
import com.example.spring_data_intro_demo.entities.User;
import com.example.spring_data_intro_demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {

        Account accountById = accountRepository.findAccountById(id);
        if (accountById != null) {
            BigDecimal currentBalance = accountById.getBalance();
            if (currentBalance.compareTo(money) >= 0) {
                BigDecimal newBalance = currentBalance.subtract(money);
                accountById.setBalance(newBalance);
                accountRepository.save(accountById);
            }
        }
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Account accountById = accountRepository.findAccountById(id);
        if (accountById != null) {
            User currentUser = accountById.getUser();
            BigDecimal currentBalance = accountById.getBalance();
            if (money.compareTo(BigDecimal.ZERO) >= 0) {
                BigDecimal newBalance = currentBalance.add(money);
                accountById.setBalance(newBalance);
                accountRepository.save(accountById);
            }
        }
    }
}
