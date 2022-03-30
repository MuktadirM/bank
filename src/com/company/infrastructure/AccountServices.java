package com.company.infrastructure;

import com.company.models.Account;
import com.company.models.Customer;
import com.company.services.IAccountServices;
import com.company.services.ICustomerServices;
import com.company.services.ITransactionServices;
import com.company.utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class AccountServices implements IAccountServices {
    private List<Account> accounts;
    private final ICustomerServices customerServices;
    private ITransactionServices transactionServices;

    public AccountServices(ICustomerServices customerServices,ITransactionServices transactionServices) {
        this.customerServices = customerServices;
        this.transactionServices = transactionServices;
    }

    @Override
    public Resource<Account> createAccount(Customer customer, double balance) {
        if(accounts == null) {
            accounts = new ArrayList<Account>();
        }
        var customerResource = customerServices.addCustomer(customer);
        if(customerResource.status == Resource.Status.ERROR) {
            assert customerResource.message != null;
            return Resource.error(customerResource.message, null);
        }
        Account account = new Account(accounts.size() + 1, customerResource.data, balance,null,null);
        transactionServices.createTransaction(account, balance);
        accounts.add(account);
        return Resource.success(account);
    }

    @Override
    public Resource<Account> getAccount(Customer customer) {
        Account result = accounts.stream().filter(account -> account.getCustomer().equals(customer)).findFirst().orElse(null);
        if(result == null) {
            return Resource.error("Account not found", null);
        }
        return Resource.success(result);
    }

    @Override
    public List<Account> getAllAccounts() {
        if(accounts == null) {
            accounts = new ArrayList<Account>();
        }
        return accounts;
    }

    @Override
    public Account getAccountById(int id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst().orElse(null);
    }
}
