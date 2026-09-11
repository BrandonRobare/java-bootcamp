# Lab 13 - Map Methods to Status Codes

## Method Table

| URI | Method | Meaning |
| --- | --- | --- |
| `/api/customers` | GET | list customers (paged) |
| `/api/customers` | POST | create a customer |
| `/api/customers/CUS-1001` | GET | fetch one customer |
| `/api/customers/CUS-1001` | PUT | replace the whole customer |
| `/api/customers/CUS-1001` | DELETE | remove the customer |
| `/api/customers/CUS-1001/status` | PATCH | change only the status (PROSPECT to ACTIVE) |
| `/api/customers/CUS-1001/interactions` | GET | list that customer's interactions |
| `/api/customers/CUS-1001/interactions` | POST | log a new interaction |

## Success Codes

| Method | Code | Body / headers |
| --- | --- | --- |
| GET collection | 200 | `CustomerPage` wrapper |
| GET item | 200 | `Customer` (Amina, CUS-1001, ACTIVE) |
| POST | 201 | `Customer` body plus `Location: /api/customers/CUS-1003` |
| PUT | 200 | replaced `Customer` |
| PATCH status | 200 | updated `Customer` |
| DELETE | 204 | no body |

Every response echoes `X-Correlation-Id` (example `lab-request-001`).

## Failure Codes

| Case | Code |
| --- | --- |
| `GET /api/customers/CUS-9999` (does not exist) | 404 Not Found |
| `PUT`, `PATCH`, `DELETE` on `CUS-9999` | 404 Not Found |
| `POST` with missing `fullName` or a malformed `email` | 400 Bad Request |
| `POST` with `customerId: CUS-1001` that already exists | 409 Conflict |
| `PATCH` status with a value outside `ACTIVE`/`PROSPECT` | 400 Bad Request |
| `GET /api/customers?size=500` (over the max of 100) | 400 Bad Request |

All failures return the shared `ErrorResponse` body: timestamp, status, error, message, path, correlationId.

## Safe vs Idempotent

Safe = the server state does not change. Idempotent = repeating the call leaves the same state as calling it once.

| Method | Safe | Idempotent | Why |
| --- | --- | --- | --- |
| GET | yes | yes | reads only |
| PUT | no | yes | replacing with the same body twice gives the same customer |
| DELETE | no | yes | second call returns 404 but the state is the same: gone |
| PATCH status | no | yes here | setting ACTIVE twice is still ACTIVE (PATCH is not idempotent in general) |
| POST | no | no | each call can create another customer |

Predict: a POST that times out and is retried can leave two customers in the database (or a 409 on the second try if the client supplies the `customerId`). That is why POST is not idempotent and why a create should return 201 with a `Location` header: a client that only gets 200 with a body does not know whether anything was created or where to find it.

## Scope
Pre-lab only: do not finish the full lab in this exercise.
