# Lab 10 pre-lab prompts

## Weak
Write a customer class.

Why weak: This prompt is vague — it doesn't name the JDK version, package, or domain conventions. An LLM may pick Spring annotations, JPA imports, or non-Northstar packages.

## Strong
Generate a plain Java 21 record named `Customer` for the Northstar CRM domain. Use only JDK 21 language and standard library types. The customer instance to show as an example should be `CUS-1001` (Amina Khan, status `ACTIVE`). Fields required: `String id`, `String fullName`, `String status`. Do NOT use Spring, JPA, or any external frameworks or annotations. Include a single-line comment with correlation note `lab-request-001`. Return only the record/class source code (no additional frameworks or build instructions).

Example output (what you expect from the strong prompt):

```java
// correlation: lab-request-001
public record Customer(String id, String fullName, String status) {
}
```

## Three constraints
1. Target JDK 21 — use only standard library types and language features available in Java 21.
2. No frameworks or annotations — explicitly exclude Spring, JPA, Lombok, etc.
3. Use fake CRM IDs only (e.g., CUS-1001) and include the correlation note `lab-request-001` in a comment.


## Notes
Save this file at `examples/module-10-exercises/notes/lab10-prelab-prompts.md` and commit to your repo. This is pre-lab practice only; do not submit.
