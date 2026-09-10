# Notifier extract plan

Lab 11 pre-lab — sketch only, nothing implemented here.

## 1. Smell

`CustomerService.updateStatus` is where the status side effect will land, and the
obvious next move is a `System.out` / email send right after `customer.setStatus(status)`.
That buries I/O in the service: unit tests get slow, order-dependent, and can only
assert on captured stdout instead of on a real collaborator.

## 2. Interface sketch (Lab 11 shape — do not implement yet)

```java
public interface CustomerNotifier {
    void notifyStatusChange(String customerId,
                            CustomerStatus from,
                            CustomerStatus to);
}
```

| Item | Plan |
| ---- | ---- |
| Interface name | `CustomerNotifier` |
| Method | `notifyStatusChange(String customerId, CustomerStatus from, CustomerStatus to)` |
| Call site in service | `CustomerService.updateStatus(...)`, after `customer.setStatus(status)`; `from` captured via `customer.getStatus()` before the set |
| Field | `private final CustomerNotifier notifier;` |
| No-arg service ctor | keep it, delegating to a no-op notifier (`(id, from, to) -> {}`) so existing `new CustomerService()` callers (`Main.java:11`) still compile |

## 3. Why this helps Copilot

Prompts that name `CustomerNotifier.notifyStatusChange` give Copilot a collaborator to
call, so it stops inventing `System.out.println` inside `CustomerService`.

## 4. Out of scope for this pre-lab

- No Spring `@EventListener` / `ApplicationEventPublisher`, no Kafka, no email provider — plain Java interface only.
- Do not implement `CustomerNotifier` or wire the constructor yet; Lab 11 does that.
- Do not write the Mockito verify test yet.

## Debug / design challenge — Kafka in the plan

If a draft plan says the notifier publishes to a Kafka topic, strip it. Lab 11 needs a
plain Java interface with no broker, no serialization, no config. Kafka would force the
test to stand up a broker (or a `@MockBean` producer) to assert one status change.
Message transport is a later concern behind the same `CustomerNotifier` seam.

## Predict the output — why tests break without the no-arg ctor

Adding only `CustomerService(CustomerNotifier notifier)` removes the implicit no-arg
constructor. Every existing `new CustomerService()` call then fails to compile —
`constructor CustomerService in class CustomerService cannot be applied to given types;
required: CustomerNotifier, found: no arguments`. In lab10-crm today that is
`Main.java:11`, plus any test that constructs the service the same way. It is a compile
error, not a test failure, so the whole module stops building and no test runs at all.
Keeping the no-arg ctor delegating to a no-op notifier keeps that code compiling while
Lab 11's new test passes a Mockito mock.

## Self-check

- [x] Method is `notifyStatusChange`, not `notifyActivated`
- [x] Smell named
- [x] Out-of-scope noted

