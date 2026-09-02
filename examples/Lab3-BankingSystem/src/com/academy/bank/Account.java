package com.academy.bank;

public abstract class Account {

    private String accountNumber;
    private double balance;
    private Customer customer;

    protected Account(String accountNumber, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void deposit(double amount) {
        // DONE: reject amount <= 0; otherwise add amount to balance
        if (amount <= 0) {
            return;
        } else {
            setBalance(getBalance() + amount);
        }
    }

    public boolean withdraw(double amount) {
        // DONE: reject amount <= 0
        if (amount <= 0) { return false; }
        // DONE: totalDeduction = amount + calculateCharges(); fail if > balance
        double totalDeduction = amount + calculateCharges();
        if (totalDeduction > getBalance()) { return false; }
        // DONE: subtract totalDeduction from balance; return true/false
        setBalance(getBalance() - totalDeduction);
        return true;
    }

    public abstract void displayAccount();

    public double calculateCharges() {
        return 0.0;
    }

    public double calculateInterest() {
        return 0.0;
    }

    public String getAccountType() {
        return "Account";
    }
}
