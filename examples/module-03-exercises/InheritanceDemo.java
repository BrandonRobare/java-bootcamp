public class InheritanceDemo {
    public static void main(String[] args) {
        Account[] accounts = {
                new Account(1000.00),
                new SavingsAccount(1000.00),
                new CurrentAccount(1000.00)
        };

        for (Account account : accounts) {
            System.out.printf("%n--- %s ---%n", account.getAccountType());
            report(account, 950.00);
            report(account, 300.00);
        }
    }

    private static void report(Account account, double amount) {
        boolean ok = account.withdraw(amount);
        System.out.printf("withdraw %.2f -> %s, balance %.2f%n",
                amount, ok ? "ok" : "refused", account.getBalance());
    }
}
