public class Account {
    // DONE: hide balance from outside code (private field already shown — focus on methods)
    private double balance;

    public Account(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                    "Initial balance cannot be negative");
        }
        balance = initialBalance;
    }

    public void deposit(double amount) {
        // DONE: reject non-positive amounts (print message, return early)
        if (amount <= 0) {
            System.out.println("deposit amount must be positive");
            return;
        } else {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        // DONE: reject if amount <= 0 OR amount > balance
        if (amount <= 0 || amount > balance) {
            System.out.println("you do not have the funds to withdraw");
            return false;
        } else {
            balance -= amount;
            return true;
        }
    }

    // DONE: read-only accessor — return balance
    public double getBalance() {
        return balance;
    }

    protected void setBalance(double newBalance) {
        balance = newBalance;
    }

    // Exercise 3 will override this method
    public String getAccountType() {
        return "Account";
    }
}
