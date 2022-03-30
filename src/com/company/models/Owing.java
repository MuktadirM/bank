package com.company.models;

public class Owing {
    private int id;
    private double amount;
    private int ownerId;
    private int receiverId;

    public Owing(double amount, int ownerId, int receiverId) {
        this.amount = amount;
        this.ownerId = ownerId;
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Owing{" +
                "id=" + id +
                ", amount=" + amount +
                ", ownerId=" + ownerId +
                ", receiverId=" + receiverId +
                '}';
    }
}
