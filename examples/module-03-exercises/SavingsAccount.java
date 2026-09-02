public class SavingsAccount extends Account {
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
        if (amount > 0 && getBalance() - amount < MIN_BALANCE) {
            System.out.printf(
                    "savings withdrawal rejected: balance must stay at or above %.2f%n",
                    MIN_BALANCE);
            return false;
        }
        return super.withdraw(amount);
    }
}
