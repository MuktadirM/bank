package com.company.infrastructure;

import com.company.models.Account;
import com.company.models.Bank;
import com.company.models.Owing;
import com.company.models.Transaction;
import com.company.services.IAccountServices;
import com.company.services.IBankServices;
import com.company.services.ITransactionServices;
import com.company.utils.Resource;
import com.company.utils.TransactionType;

import java.util.ArrayList;
import java.util.List;


public class BankServices implements IBankServices {
    private ITransactionServices transactionServices;
    private IAccountServices accountServices;
    private Bank bank;

    public BankServices(ITransactionServices transactionServices,Bank bank, IAccountServices accountServices) {
        this.transactionServices = transactionServices;
        this.bank = bank;
        this.accountServices = accountServices;
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public boolean login(String username) {
        return false;
    }

    @Override
    public Resource<Transaction> deposit(Account account, double amount) {
        var result = transactionServices.createTransaction(account,amount);
        if(result.status == Resource.Status.SUCCESS)
        {
            var owing = getOwing(account);
            if(owing != null && owing.size()>0){
                if(owing.get(owing.size()-1).getAmount()>amount){
                    owing.get(owing.size()-1).setAmount(owing.get(owing.size()-1).getAmount()-amount);
                    transactionServices.updateOwing(owing.get(owing.size()-1));
                    account.setOwings(owing);
                } else{
                    transactionServices.deleteOwing(owing.get(owing.size()-1));
                    owing.get(owing.size()-1).setAmount(0);
                    account.setOwings(owing);
                }
            }
            return result;
        }
        return Resource.error("Transaction fail",null);
    }

    @Override
    public Resource<Transaction> transfer(Account sender, Account receiver, double amount) {
        if(amount > getBalance(sender)){
            var owing = new Owing(amount - getBalance(sender),sender.getId(),receiver.getId());
            transactionServices.addOwing(owing);
            List<Owing> list;
            if(sender.getOwings() != null){
                list = sender.getOwings();
            } else{
                list = new ArrayList<>();
            }
            list.add(owing);
            sender.setOwings(list);
        }

        var result = transactionServices.createTransaction(sender,receiver,amount);
        if(result.status == Resource.Status.SUCCESS)
        {
            return result;
        }
        return Resource.error("Transaction fail",null);
    }

    @Override
    public double getBalance(Account account) {
        var result = transactionServices.getAllTransactionsByAccount(account);

        if(result.status == Resource.Status.SUCCESS) {
            assert result.data != null;
            var deposits = result.data.stream().filter(transaction -> transaction.getType() == TransactionType.DEPOSIT).toList();

            var totalDeposit = deposits.stream().mapToDouble(Transaction::getAmount).sum();

            var transfers = result.data.stream().filter(transaction -> transaction.getType() == TransactionType.TRANSFER && transaction.getAccountId() == account.getId()).toList();
            var transfersReceived = result.data.stream().filter(transaction -> transaction.getType() == TransactionType.TRANSFER && transaction.getRecipientId() == account.getId()).toList();
            var totalTransferRec = transfersReceived.stream().mapToDouble(Transaction::getAmount).sum();

            var totalTransfer = transfers.stream().mapToDouble(Transaction::getAmount).sum();

            return totalTransferRec+totalDeposit - totalTransfer;
        }
        return 0;
    }

    @Override
    public List<Owing> getOwing(Account account) {
        var all = transactionServices.getAllOwingByAccount(account);
        if(all.status == Resource.Status.SUCCESS && all.data != null){
            return all.data.stream().filter(owing -> owing.getOwnerId() == account.getId()).toList();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Owing> owingReceiving(Account account){
        var all = transactionServices.getAllOwingByAccount(account);
        if(all.status == Resource.Status.SUCCESS && all.data != null){
            return all.data.stream().filter(owing -> owing.getReceiverId() == account.getId()).toList();
        }
        return new ArrayList<>();
    }

}
