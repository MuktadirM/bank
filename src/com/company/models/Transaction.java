package com.company.models;

import com.company.utils.TransactionType;


public class Transaction {
    private int id;
    private final TransactionType type;
    private final double amount;
    private final int accountId;
    private int recipientId;

    public Transaction(TransactionType type, double amount, int accountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
    }

    public Transaction(TransactionType type, double amount, int accountId, int recipientId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.recipientId = recipientId;
    }

    public Transaction(int id, TransactionType type, double amount, int accountId, int recipientId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.recipientId = recipientId;
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", accountId=" + accountId +
                ", recipientId=" + recipientId +
                '}';
    }
}
