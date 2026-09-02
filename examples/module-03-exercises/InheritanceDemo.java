public class InheritanceDemo {
    public static void main(String[] args) {
        // Both subclasses held in Account references — the compiler only sees Account,
        // the JVM picks the override from the object's runtime type.
        Account[] accounts = {
                new Account(1000.00),
                new SavingsAccount(1000.00),
                new CurrentAccount(1000.00)
        };

        for (Account account : accounts) {
            System.out.printf("%n--- %s ---%n", account.getAccountType());
            report(account, 950.00);   // under Savings' minimum, fine for the other two
            report(account, 300.00);   // only Current can go negative to cover it
        }
    }

    // Takes an Account, never a subclass — this method never changes when a new
    // account type is added.
    private static void report(Account account, double amount) {
        boolean ok = account.withdraw(amount);
        System.out.printf("withdraw %.2f -> %s, balance %.2f%n",
                amount, ok ? "ok" : "refused", account.getBalance());
    }
}
