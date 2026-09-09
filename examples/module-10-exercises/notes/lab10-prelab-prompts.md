# Lab 10 pre-lab prompts

## Weak
Write a customer class.

Why weak: The prompt is underspecified — it omits the JDK target, package, domain conventions, and explicit exclusions. An LLM may assume Spring or JPA annotations, choose non-Northstar package names, or introduce external libraries and annotations not allowed in the course environment.

## Strong
Generate a plain Java 21 record named `Customer` in the Northstar CRM domain. Use only JDK 21 language features and standard library types. Provide an example instance using fake CRM id `CUS-1001` for Amina Khan with status `ACTIVE`. Required fields: `String id`, `String fullName`, `String status`. DO NOT use Spring, JPA, Lombok, or any external frameworks or annotations. Include a single-line comment with correlation note `lab-request-001`. Return only the Java record/class source code.

Example expected output:

```java
// correlation: lab-request-001
public record Customer(String id, String fullName, String status) {
}

// example usage (not required in final source):
// var c = new Customer("CUS-1001", "Amina Khan", "ACTIVE");
```

## Three constraints
1. Target JDK 21 — use only language features and standard library APIs available in Java 21.
2. No frameworks or annotations — explicitly exclude Spring, JPA, Lombok, and any external dependencies.
3. Use only fake CRM IDs (e.g., `CUS-1001`) and include the correlation comment `lab-request-001` for traceability.
