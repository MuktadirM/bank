package com.company.services;

import com.company.models.Account;
import com.company.models.Customer;
import com.company.models.Transaction;
import com.company.utils.Resource;

import java.util.List;

public interface IAccountServices {
    Resource<Account> createAccount(Customer customer, double balance);
    Resource<Account> getAccount(Customer customer);
    List<Account> getAllAccounts();
    Account getAccountById(int id);

}
