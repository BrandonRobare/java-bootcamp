public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 500.00;

    public CurrentAccount(double initialBalance) {
        super(initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("withdrawal amount must be positive");
            return false;
        }
        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            System.out.printf(
                    "current withdrawal rejected: overdraft limit is %.2f%n",
                    OVERDRAFT_LIMIT);
            return false;
        }
        setBalance(getBalance() - amount);
        return true;
    }
}
