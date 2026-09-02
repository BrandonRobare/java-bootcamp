public class CurrentAccount extends Account {
    // A current account may go negative, but only this far.
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
        // No super.withdraw() here: Account refuses amount > balance, which is exactly
        // the rule an overdraft exists to relax. The amount check still has to happen.
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
