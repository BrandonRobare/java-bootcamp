# Banking domain notes

Module 3 - Exercise 1

| Entity | Identity | Important attributes | Main responsibility |
| ------ | -------- | -------------------- | ------------------- |
| Customer | customerId | name, email, phone | Maintain the customer profile and the list of accounts it owns |
| Account | accountNumber | owner, balance, accountType | Protect the balance and perform validated deposits and withdrawals |
| Transaction | transactionId | account, type, amount, timestamp | Record one completed account operation |

## Relationships

- One Customer can own zero or more Accounts.
- One Account belongs to exactly one Customer.
- One Account can have many Transactions.
- One Transaction belongs to exactly one Account.

## Rules

- An account balance cannot be changed directly from outside Account.
- A deposit amount must be positive.
- A withdrawal cannot exceed the allowed balance.
- A Transaction is written only after the Account accepts the operation.

## Design decision

Account owns the balance, so it also owns the rules that keep the balance valid; putting
the check anywhere else means the invariant is only as good as every caller remembering to
apply it. Main is a thin coordinator. It reads input and prints results, and asks Account
to perform the operation. When SavingsAccount and CurrentAccount later override withdrawal
with different limits, the rule stays with the class that knows it.

## Not entities

Menu option, printed line, and the database are implementation and infrastructure, not
things the banking domain gives an identity to.

Module 3 - Exercise 6

## SRP spot-check

The original method could change because the formula changes or because
the output format changes. These are separate responsibilities.

> `Main` should manage menu input, `BankService` should coordinate banking operations, and domain classes should protect their own state.

Module 3 - Exercise 7

## OCP - Open/Closed

Adding FrozenAccount required no edits inside SavingsAccount or CurrentAccount; it arrived
as a new class and InheritanceDemo's existing loop picked it up unchanged.

## LSP - Liskov Substitution

FrozenAccount refuses a withdrawal by returning false and leaving the balance at 100.00,
which is exactly what Account.withdraw already promises; throwing instead would force every
caller of the shared loop to add a try/catch it never needed before.

## ISP - Interface Segregation

If Printable also required sendEmailReceipt(), SavingsAccount would be forced to implement a
method it has no email system to call, leaving it a stub or a thrown exception - the fat
interface would push a failure into a class that only wanted to print itself.

## DIP - Dependency Inversion

Declaring Account account = new FrozenAccount(100.00) makes the calling code depend on the
shared contract instead of one subclass, so swapping in a different account type later
changes the single line that constructs it and nothing that uses it.

## The five letters

Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency
Inversion.
