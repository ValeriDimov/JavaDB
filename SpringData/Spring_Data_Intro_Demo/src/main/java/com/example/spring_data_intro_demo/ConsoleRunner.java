package com.example.spring_data_intro_demo;

import com.example.spring_data_intro_demo.entities.Account;
import com.example.spring_data_intro_demo.entities.User;
import com.example.spring_data_intro_demo.services.AccountService;
import com.example.spring_data_intro_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private AccountService accountService;
    private UserService userService;

    @Autowired
    public ConsoleRunner(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Peter", 32);

        Account account = new Account(new BigDecimal(25000));
        account.setUser(user);

        user.setAccounts(new HashSet<>(){
            {add(account);
            }
        });

        userService.registerUser(user);

        accountService.withdrawMoney(new BigDecimal(20000), account.getId());
        accountService.transferMoney(new BigDecimal(30000), account.getId());


    }
}
