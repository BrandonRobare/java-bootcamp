package com.academy.bank;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class BankService {

    private static final int MAX_CUSTOMERS = 50;
    private static final int MAX_ACCOUNTS = 100;
    private static final int MAX_TRANSACTIONS = 500;

    private final Customer[] customers = new Customer[MAX_CUSTOMERS];
    private final Account[] accounts = new Account[MAX_ACCOUNTS];
    private final Transaction[] transactions = new Transaction[MAX_TRANSACTIONS];

    private int customerCount = 0;
    private int accountCount = 0;
    private int transactionCount = 0;
    private int nextAccountNumber = 10001;
    private int nextTransactionNumber = 1;

    private final Scanner scanner;

    public BankService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void createCustomer() {
        // DONE: read customerId / name / email / phone; reject duplicate IDs
        System.out.print("Customer ID : ");
        String customerId = scanner.nextLine().trim();

        if (findCustomer(customerId) != null) {
            System.out.println("Customer ID already exists");
            return;
        }

        System.out.print("Name : ");
        String name = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

        System.out.print("Phone : ");
        String phone = scanner.nextLine();

        // DONE: store new Customer; print "Customer Created Successfully."
        customers[customerCount++] = new Customer(customerId, name, email, phone);
        System.out.println("Customer Created Successfully.");
    }

    public void createSavingsAccount() {
        // DONE: read existing customer, initial balance, interest rate
        Customer customer = readExistingCustomer();
        if (customer == null) { return; }
        double initialBalance = readPositiveAmount("Initial Balance : ");
        double interestRate = readPositiveAmount("Interest Rate (%) : ");

        // DONE: create SavingsAccount with nextAccountNumber++; store in accounts[]
        String accountNumber = String.valueOf(nextAccountNumber++);
        accounts[accountCount++] = new SavingsAccount(accountNumber, initialBalance, customer, interestRate);

        System.out.println("Savings Account Created.");
        System.out.println("Account Number : " + accountNumber);
        System.out.printf("Balance : %.0f%n", initialBalance);
        System.out.printf("Interest Rate : %.0f%%%n", interestRate);
    }

    public void createCurrentAccount() {
        // DONE: read existing customer, initial balance, transaction fee
        Customer customer = readExistingCustomer();
        if (customer == null) { return; }
        double initialBalance = readPositiveAmount("Initial Balance : ");
        double transactionFee = readPositiveAmount("Initial Transaction Fee : ");

        // TODO: create CurrentAccount with nextAccountNumber++; store in accounts[]
        Account CurrentAccount = new

    }

    public void deposit() {
        // TODO: read existing account + amount; account.deposit; recordTransaction DEPOSIT
        // TODO: print updated balance
        throw new UnsupportedOperationException("TODO");
    }

    public void withdraw() {
        // TODO: read existing account + amount; account.withdraw; record on success
        // TODO: for CurrentAccount, print fee + total deducted; print updated balance
        throw new UnsupportedOperationException("TODO");
    }

    public void displayAccounts() {
        // TODO: if empty print message; else loop displayAccount() for each
        throw new UnsupportedOperationException("TODO");
    }

    public void displayCustomers() {
        if (customerCount == 0) {
            System.out.println("No customers available.");
            return;
        }

        System.out.println("----------------------------------");
        for (int i = 0; i < customerCount; i++) {
            customers[i].display();
            System.out.println("----------------------------------");
        }
    }

    public void transferMoney() {
        System.out.println("Bonus / full-path feature — implement after core TODOs.");
    }

    public void displayTransactionHistory() {
        System.out.println("Bonus / full-path feature — implement after core TODOs.");
    }

    public void displayAccountsSortedByBalance() {
        System.out.println("Bonus / full-path feature — implement after core TODOs.");
    }

    public void displayHighestBalanceCustomer() {
        System.out.println("Bonus / full-path feature — implement after core TODOs.");
    }

    public void generateAccountSummaryReport() {
        System.out.println("Bonus / full-path feature — implement after core TODOs.");
    }

    private Customer readExistingCustomer() {
        if (customerCount == 0) {
            System.out.println("Create a customer first.");
            return null;
        }

        System.out.print("Customer ID : ");
        String customerId = scanner.nextLine().trim();
        Customer customer = findCustomer(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
        }

        return customer;
    }

    private Account readExistingAccount() {
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return null;
        }

        System.out.print("Account Number : ");
        String accountNumber = scanner.nextLine().trim();
        Account account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
        }

        return account;
    }

    private Customer findCustomer(String customerId) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getCustomerId().equalsIgnoreCase(customerId)) {
                return customers[i];
            }
        }
        return null;
    }

    private Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    private void recordTransaction(String accountNumber, double amount, String type) {
        if (transactionCount >= MAX_TRANSACTIONS) {
            return;
        }

        String transactionId = "T" + nextTransactionNumber++;
        String date = LocalDate.now().toString();
        transactions[transactionCount++] = new Transaction(transactionId, amount, type, date, accountNumber);
    }

    private double readPositiveAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Amount must not be negative.");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid amount. Please try again.");
            }
        }
    }
}
