package com.company.services;

import com.company.models.Account;
import com.company.models.Owing;
import com.company.models.Transaction;
import com.company.utils.Resource;

import java.util.List;

public interface ITransactionServices {
    Resource<Transaction> createTransaction(Account account, double amount);
    Resource<Transaction> createTransaction(Account account, Account receiver ,double amount);
    Resource<List<Transaction>> getAllTransactionsByAccount(Account account);
    Resource<List<Transaction>>getAllTransactions();
    void addOwing(Owing owing);
    Resource<List<Owing>> getAllOwingByAccount(Account account);
    void updateOwing(Owing owing);
    void deleteOwing(Owing owing);
}
