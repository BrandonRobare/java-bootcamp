public class AbstractDemo {
    public static void main(String[] args) {
        AbstractAccount account = new AbstractSavings(500.00);

        System.out.println("account type: " + account.getAccountType());
        account.deposit(250.00);
        account.withdraw(100.00);
        System.out.printf("balance: %.2f%n", account.getBalance());

    }
}
