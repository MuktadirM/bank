package com.company.infrastructure;

import com.company.models.Account;
import com.company.models.Owing;
import com.company.models.Transaction;
import com.company.services.ITransactionServices;
import com.company.utils.Resource;
import com.company.utils.TransactionType;

import java.util.ArrayList;
import java.util.List;


public class TransactionServices implements ITransactionServices {
    private List<Owing> owings;
    private List<Transaction> transactions;

    public TransactionServices() {
        transactions = new ArrayList<>();
    }

    @Override
    public Resource<Transaction> createTransaction(Account account, double amount) {
        Transaction transaction = new Transaction(transactions.size() + 1, TransactionType.DEPOSIT, amount, account.getId(), -1);
        transactions.add(transaction);
        return Resource.success(transaction);
    }

    @Override
    public Resource<Transaction> createTransaction(Account account, Account receiver, double amount) {
        Transaction transaction = new Transaction(transactions.size() + 1, TransactionType.TRANSFER, amount, account.getId(), receiver.getId());
        transactions.add(transaction);
        return Resource.success(transaction);
    }


    @Override
    public Resource<List<Transaction>> getAllTransactionsByAccount(Account account) {
        var trans = transactions.stream().filter(transaction -> transaction.getAccountId() == account.getId() || transaction.getRecipientId() == account.getId()).toList();
        return Resource.success(trans);
    }

    @Override
    public Resource<List<Transaction>> getAllTransactions() {
        return Resource.success(transactions);
    }

    @Override
    public void addOwing(Owing owing) {
        if (owings == null) {
            owings = new ArrayList<>();
        }
        owing.setId(owings.size() + 1);
        owings.add(owing);
    }

    @Override
    public Resource<List<Owing>> getAllOwingByAccount(Account account) {
        if(owings == null) {
            owings = new ArrayList<>();
        }
        var owingsAll = owings.stream().filter(owing -> owing.getOwnerId() == account.getId() ||  owing.getReceiverId() == account.getId()).toList();
        return Resource.success(owingsAll);
    }

    @Override
    public void updateOwing(Owing owing) {
        var find = owings.stream().filter(o -> o.getId() == owing.getId()).findFirst();
        find.ifPresent(value -> owings.remove(value));
        owings.add(owing);
    }

    @Override
    public void deleteOwing(Owing owing) {
        var find = owings.stream().filter(o -> o.getId() == owing.getId()).findFirst();
        find.ifPresent(value -> owings.remove(value));
    }
}
