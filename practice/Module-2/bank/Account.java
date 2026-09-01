package bank;   // "bank" package == the bank/ folder

// must be public because anything outside the bank package can only see public types
public class Account {
    private final String owner;
    private double balance;

    public Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public String toString() {
        return owner + ": $" + balance;
    }
}
