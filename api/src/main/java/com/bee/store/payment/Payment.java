package com.bee.store.payment;

public interface Payment {
    void pay(double amount, String accountNumber, String accountName);
}
