package com.company.models;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Account {
    private int id;
    private Customer customer;
    private double initialBalance;
    @Nullable
    private List<Transaction> transactions;
    @Nullable
    private List<Owing> owings;

    public Account(int id, Customer customer, double initialBalance, @Nullable List<Transaction> transactions, @Nullable List<Owing> owings) {
        this.id = id;
        this.customer = customer;
        this.initialBalance = initialBalance;
        this.transactions = transactions;
        this.owings = owings;
    }

    public Account(Customer customer, double initialBalance, @Nullable List<Transaction> transactions, @Nullable List<Owing> owings) {
        this.customer = customer;
        this.initialBalance = initialBalance;
        this.transactions = transactions;
        this.owings = owings;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    @Nullable
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(@Nullable List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Nullable
    public List<Owing> getOwings() {
        return owings;
    }

    public void setOwings(@Nullable List<Owing> owings) {
        this.owings = owings;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customer=" + customer +
                ", initialBalance=" + initialBalance +
                ", transactions=" + transactions +
                ", owings=" + owings +
                '}';
    }
}
