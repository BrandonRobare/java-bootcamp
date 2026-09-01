// BUILT-IN package
import java.util.List;   // SINGLE-TYPE import
import java.util.*;      // ON-DEMAND import

// USER-CREATED package
import bank.Account;     // SINGLE-TYPE import
import bank.*;           // ON-DEMAND import

public class packagesAndImports {
    public static void main(String[] args) {
        // java.lang is imported automatically because String, System and Math don't need an import
        System.out.println("no import needed (java.lang): Math.max(3, 9) = " + Math.max(3, 9));

        // java.util: List came from single-type import
        // ArrayList and Arrays came from on-demand import
        List<String> names = new ArrayList<>(Arrays.asList("Anita", "Brandon", "Chen"));
        names.add("Dana");
        System.out.println("built-in, both import forms: " + names);

        // bank.Account with single-type import
        Account account = new Account("Anita", 1000.00);
        account.deposit(250.00);
        System.out.println("user package, single import: " + account);

        // bank.Interest is reachable ONLY because of import bank.*
        double earned = Interest.simple(account.getBalance(), 0.05, 2);
        System.out.println("user package, on-demand import: $" + earned + " interest");

        // no import
        java.time.LocalDate today = java.time.LocalDate.now();
        System.out.println("fully qualified, no import: " + today);
    }
}
