package com.company.services;

import com.company.models.Account;
import com.company.models.Bank;
import com.company.models.Owing;
import com.company.models.Transaction;
import com.company.utils.Resource;

import java.util.List;

public interface IBankServices {
    Bank getBank();
    boolean login(String username);
    Resource<Transaction> deposit(Account account, double amount);
    Resource<Transaction> transfer(Account sender, Account receiver,double amount);
    double getBalance(Account account);
    List<Owing> getOwing(Account account);
    List<Owing> owingReceiving(Account account);
}
