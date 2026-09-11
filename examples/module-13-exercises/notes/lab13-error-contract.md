# Lab 13 - Write the Error Contract

## Error Fields

One envelope for every error, from every endpoint: `timestamp` (ISO-8601), `status` (HTTP status code), `error` (HTTP reason phrase, for humans), `code` (machine-readable, e.g. `CUSTOMER_NOT_FOUND`), `message` (human-readable, safe to show), `path`, `correlationId`, and optional `details` (per-field validation errors).

## Correlation

`correlationId` echoes the inbound `X-Correlation-Id` header; if absent, the server generates a value. Support traces a reported failure by this one id.

## Two Examples

404, GET `/api/v1/customers/CUS-9999`:

```json
{
  "timestamp": "2026-08-23T21:00:00Z",
  "status": 404,
  "error": "Not Found",
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Customer not found: CUS-9999",
  "path": "/api/v1/customers/CUS-9999",
  "correlationId": "lab-request-001"
}
```

400, POST `/api/v1/customers` with a malformed body:

```json
{
  "timestamp": "2026-08-23T21:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "code": "VALIDATION_FAILED",
  "message": "Request body failed validation",
  "path": "/api/v1/customers",
  "correlationId": "lab-request-001",
  "details": [
    { "field": "email", "message": "must be a valid email" },
    { "field": "fullName", "message": "must not be blank" }
  ]
}
```

## Leak Check

Never include stack traces, raw SQL, internal hostnames, or another customer's data. A leaked Hibernate trace exposes the ORM/JVM internals (library versions and class names, so an attacker knows which CVEs to try) and the table and column names plus query parameters from the SQL, which map out the schema and reveal what data was being looked up.

## Predict

The Angular interceptor has to branch on error shape per endpoint. One envelope lets it switch on `code` instead of parsing `message` text, and highlight inputs from `details` without string-splitting.

## Scope

Pre-lab only: do not finish the full lab in this exercise.
