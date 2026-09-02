public class SavingsAccount extends Account {
    // A savings account must keep this much in it at all times.
    private static final double MIN_BALANCE = 100.00;

    public SavingsAccount(double initialBalance) {
        super(initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public boolean withdraw(double amount) {
        // balance is private in Account, so read it through the accessor
        if (amount > 0 && getBalance() - amount < MIN_BALANCE) {
            System.out.printf(
                    "savings withdrawal rejected: balance must stay at or above %.2f%n",
                    MIN_BALANCE);
            return false;
        }
        return super.withdraw(amount);   // Account still owns the shared rules
    }
}
