# Module 11 — GitHub Copilot for Testing and Refactoring

Every code example from the Module 11 slides, made runnable. The slide snippets are fragments;
these compile and go red when you break the thing they claim to protect.

## Run it

```bash
cd ~/java-bootcamp/practice/Module-11
mvn clean test
```

```
Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Mockito self-attaches its agent on JDK 21 and prints a warning about it. Harmless here.

---

## 1. Slide → file

| Slide | Concept | Where it lives |
| ----- | ------- | -------------- |
| 6, 8 | AAA structure, generated unit test | `CustomerServiceTest.activate_prospectRavi_setsStatusActive` |
| 10, 11 | Edge-case table → one test per row | `CustomerServiceTest` (null, blank, null status, unknown id, duplicate) |
| 14, 16 | Mocks, `verify()`, stubbed failure | `CustomerNotifierMockTest` |
| 20 | Validate-first refactor | `CustomerService.activate` |
| 22, 23 | Magic number / string, Extract Method | `smells/DiscountPricing` |
| 25 | Extract the notifier collaborator | `service/CustomerNotifier` |
| 27 | Naming: `c(p,r,y,m)` → `computeCompoundInterest` | `DiscountPricing.computeCompoundInterest` |
| 29 | Domain types over primitives | `entity/CustomerStatus` |
| 34 | Trivial vs meaningful assertions | `TrivialVsRealAssertTest` |
| 43 | Extract `validateCustomerId()` | `CustomerService.validateCustomerId` |

Fixtures are the ones the exercises use: **Amina = CUS-1001**, **Ravi = CUS-1002 PROSPECT**.

---

## 2. The edge-case table, as tests

Slide 10 is a table. Slide 11 says turn each row into one test with one name. That mapping:

```
   row                        test                                    asserts
   ------------------------   -------------------------------------   ------------------------------
   customerId is null         activate_nullId_throwsIllegalArgument   IllegalArgumentException.class
   customerId is ""           activate_blankId_throwsIllegalArgument  IllegalArgumentException.class
   status is null             activate_nullStatus_throws...           IllegalArgumentException.class
   ID not found               activate_unknownId_throwsNotFound       CustomerNotFoundException.class
   same status repeated       activate_toSameStatus_neverNotifies     verify(notifier, never())
   notifier throws            activate_notifierFails_stillUpdates...  status == ACTIVE, verify times(1)
   same ID inserted twice     addCustomer_duplicateId_isRejected      IllegalStateException.class
```

Two rows need a **different type**, not just "it threw". A malformed id and an unknown id are
different bugs; asserting `Exception.class` on both would pass even if the service confused them.

---

## 3. Why the notifier had to be extracted

Before — the smell from Exercise 2:

```
   +-----------------------------------------------+
   |  CustomerService.activate()                   |
   |    customer.setStatus(ACTIVE)                 |
   |    System.out.println("Activated " + id)      |  <-- test cannot see this
   |    emailClient.send(customer.getEmail(), ...) |  <-- test cannot stop this
   +-----------------------------------------------+
```

After:

```
   +---------------------------+        +--------------------------+
   |  CustomerService          | -----> |  CustomerNotifier        |  interface
   |    customer.setStatus()   |        |    notifyActivated(id)   |
   |    notifier.notify...(id) |        +--------------------------+
   +---------------------------+              ^            ^
                                              |            |
                                   +----------------+  +------------------+
                                   | EmailNotifier  |  | Mockito mock     |
                                   | (production)   |  | (the test)       |
                                   +----------------+  +------------------+
```

The service never learns which one it got. That is the whole trick — and it is why
`verify(notifier, times(1))` can exist at all.

---

## 4. `assertEquals` and `verify` catch different bugs

```
                              state assert       verify()
   status not updated            RED               green
   email never sent             green               RED
   email sent twice             green               RED
```

A test with only `assertEquals` stays green while every activation email silently stops going out.
Slide 16's point: check the state *and* the interaction.

---

## 5. The test that lies

```java
assertNotNull(customer);   // activate() can never return null
assertTrue(true);          // ...
```

Break `activate` so it sets `SUSPENDED`. Run it. Still green.

That is knowledge-check Q4 and Exercise 3 in one line: a test that cannot fail is a green build
telling you a lie, and it is what Copilot writes first if you accept the first suggestion.
Reject any generated test whose assertions never name a domain value.

To feel it, do the slide-44 mutation check by hand:

```bash
# comment out `customer.setStatus(status);` in CustomerService.activate, then:
mvn -q test
```

```
Tests run: 16, Failures: 4
  activate_prospectRavi_setsStatusActive   expected: <ACTIVE> but was: <PROSPECT>
  activate_amina_hasActiveStatus           expected: <ACTIVE> but was: <PROSPECT>
  activate_notifierFails_stillUpdatesStatus expected: <ACTIVE> but was: <PROSPECT>
  activate_statusAlreadySet_sendsNoNotification  wanted 1 time, was 2
```

Four tests caught it. An `assertNotNull`-only suite would have caught zero. Put the line back.

---

## 6. Refactor means the tests do not change

`DiscountPricingTest` was written against the slide's smelly version:

```java
double price = 0.90 * base;
if (tier.equals("VIP")) price = 0.80 * base;
```

and passes unchanged against the clean one. Same money out, better names in. If a "refactor"
makes you edit assertions, it was a behavior change wearing a refactor's clothes.

The one exception is documented in the test: `nullTier_fallsBackToStandard` would have thrown
`NullPointerException` before. Invalid input got better; valid input did not move. Slide 20's rule.

---

## Files

| File | Its one job |
| ---- | ----------- |
| `entity/Customer.java` | one customer; identity is the business key |
| `entity/CustomerStatus.java` | the enum from slide 29 |
| `exception/CustomerNotFoundException.java` | unknown id is its own failure type |
| `service/CustomerNotifier.java` | the extracted collaborator — the seam a mock plugs into |
| `service/CustomerService.java` | class under test; validate-first, notifier injected |
| `smells/DiscountPricing.java` | magic numbers, Extract Method, naming |
| `service/CustomerServiceTest.java` | AAA + one test per edge-case row |
| `service/CustomerNotifierMockTest.java` | `@Mock`, `verify`, `never`, `doThrow` |
| `service/TrivialVsRealAssertTest.java` | the rejected test, kept as a comment for contrast |
| `smells/DiscountPricingTest.java` | proof the refactor preserved behavior |
