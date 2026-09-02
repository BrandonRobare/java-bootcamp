public class InheritanceDemo {
    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount(100.00),
            new CurrentAccount(100.00)
        };

        for (Account account : accounts) {
            account.withdraw(20.00);
            System.out.printf("%s balance: %.2f%n",
                    account.getAccountType(),
                    account.getBalance());
        }
    }
}
