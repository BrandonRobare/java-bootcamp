# Lab 7 - Reflection Answers

## 1. How would a future CRM map domain exceptions to API errors?

The same boundary-catch pattern moves up a layer: instead of `executeTransaction`
printing to the console, a controller catches the domain exception and translates
it to a status code — `AccountNotFoundException` to 404, `InvalidAmountException`
to 400, an unhandled type to 500. The full stack trace goes to the log and the
caller gets only a safe message, exactly as the ATM shows "Insufficient Balance"
while `logs/application.log` keeps the trace. No CRM exists today; this is the
pattern that would carry over.

## 2. What is the difference between checked and unchecked exceptions?

Checked exceptions extend `Exception` and the compiler forces every caller to
catch or declare them — removing `throws InsufficientFundsException` from
`Account.withdraw` produces "unreported exception," which is how the contract is
enforced. Unchecked exceptions extend `RuntimeException` and carry no such
requirement, so `NullPointerException` compiles fine and fails at runtime.

## 3. Why should custom exceptions be used?

A named type lets each failure get its own catch branch and its own user message,
instead of parsing strings out of a generic `Exception`. They also carry domain
data: `InsufficientFundsException` holds requested and available amounts, so the
log line reads "Requested 20000.0 Balance 11000.0" without the handler having to
go look those values up.
