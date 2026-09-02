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
