# Customer sketch notes (CUS-1001 Amina Khan)

| customerId | fullName   | status   |
| ---------- | ---------- | -------- |
| CUS-1001   | Amina Khan | ACTIVE   |
| CUS-1002   | Ravi Singh | PROSPECT |

Correlation `lab-request-001`: logs/headers only — not a Customer field.

Boundary: sketch only — pre-lab. Do not add Spring/JPA annotations; use plain Java fields.

Debug / design note: reject adding `@Entity` in this sketch because exercise requires "plain Java". Suggested prompt line to prevent it: "Do not include any framework annotations or imports (no Spring, JPA, or Lombok) — plain Java classes and enums only."
