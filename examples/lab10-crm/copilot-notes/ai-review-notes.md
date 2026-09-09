# AI review notes — Lab 10

## lab10-001 — weak vs strong (entity)
- Date: 2026-09-09
- Weak prompt used: // customer class
- Output summary: Copilot suggested a JPA-style entity with Long id and @Entity annotations (rejected).
- Strong prompt used: // Java entity class Customer in package com.northstar.crm.entity representing a Northstar CRM customer. Fields: customerId (String, format "CUS-1001"), fullName (String), email (String), phone (String), status (CustomerStatus enum), createdAt (LocalDateTime). No-args constructor, all-args constructor, getters and setters, equals/hashCode based only on customerId, toString.
- Output summary: Generated a plain POJO matching the requested fields and methods. No persistence annotations accepted.
- Decision: accept
- Reason (1 sentence): Strong prompt prevented JPA hallucination and produced the required POJO shape.

## lab10-002 — weak vs strong (addCustomer)
- Date: 2026-09-09
- Decision: accept
- Reason: Implemented guard clauses for null/blank customerId and duplicate checks throwing appropriate exceptions.

## lab10-003 — CustomerStatus / Customer scaffold
- Rejected JPA? yes
- Notes: Removed/avoided any jakarta.persistence or Spring annotations. equals/hashCode implemented only on customerId as required.

## lab10-004 — CustomerService review
- Notes:
  1. Avoided typing real PII; used CUS-1001 / CUS-1002 and example emails.
  2. If Copilot suggests verbatim library code, inspect license/source and reimplement or cite before accepting.
  3. Team rule: do not merge AI-generated code until one human documents line-by-line what was accepted and why; unclear code must be rejected or rewritten.

## Failure Experiments

1. Experiment: Ask Chat to add a save method with no context
   - Action: did not run Copilot; simulated expected bad suggestion review.
   - Observe: typical hallucination is an invented DB layer or @Entity usage; would be rejected and removed.
   - Restore / conclude: do not accept; remove annotations and recompile.

2. Experiment: Disable Copilot and add deleteCustomer(String) by hand
   - Action: implemented deleteCustomer(String) manually in CustomerService.java.
   - Observe: compile succeeds without AI assistance.
   - Restore / conclude: confirmed the project can be completed without Copilot.

3. Experiment: Draft (do not send) a Chat prompt with a fake SSN/password as example
   - Action: drafted nothing that contains real PII; used CUS-1001/CUS-1002 only.
   - Observe: do not paste even fake sensitive values into Chat. Rewrote prompts to use safe IDs.
   - Restore / conclude: always sanitize example data.

4. Experiment: Ask Chat to build the entire CRM service layer in one shot
   - Action: did not accept oversized dump; used scoped prompts for each class/method.
   - Observe: large dumps are hard to review and often include unwanted framework imports.
   - Restore / conclude: prefer small, reviewed prompts per method/class.
