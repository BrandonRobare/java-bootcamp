
## Module 7 Exercise 7: error handling strategies

Implemented here: Retry (bounded loop, 3 attempts) and Fallback (return 0).

- Skip & Continue: importing 10,000 CSV rows — one bad row should not stop the other 9,999.
- Fail Fast: a required config value is missing at startup — do not limp along with a null.
- Graceful Degradation: a recommendations service is down — show the page without recommendations instead of a 500 error.
- Circuit Breaker: a downstream payment API is timing out repeatedly — stop hammering it and fail fast for a cooldown period.

Retry only transient failures. IllegalArgumentException is bad input, not a
timing problem — attempt 900 fails exactly like attempt 1.
