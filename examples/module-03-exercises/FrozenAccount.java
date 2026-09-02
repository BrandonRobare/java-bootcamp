public class FrozenAccount extends Account {
    public FrozenAccount(double initialBalance) {
        // Reuse Account construction and balance encapsulation.
        super(initialBalance);
    }

    @Override
    public boolean withdraw(double amount) {
        // DONE: refuse every withdrawal; return false (do not call super.withdraw)
        return false;
    }

    @Override
    public String getAccountType() {
        // DONE: return "Frozen"
        return "Frozen";
    }
}
