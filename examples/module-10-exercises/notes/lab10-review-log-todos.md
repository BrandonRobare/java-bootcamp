# Lab 10 review-log TODOs

Prompt strength: Moderate — model produced useful class shapes but suggested framework annotations that must be rejected.
Phantom annotation found? Yes — @MyPhantomAnnotation and `jakarta.persistence.*` imports (Reject)
Fixture check Amina status: ACTIVE
Fixture check Ravi status: PROSPECT
JDK/Maven note: JDK 21; no additional Maven dependencies for pre-lab sketches
Accept / Reject / Edit: Edit — removed phantom annotations and JPA imports; ensured Ravi remains PROSPECT.

Reject reason: Copilot suggested invented annotation `@MyPhantomAnnotation` and JPA imports (`jakarta.persistence`) which are out-of-scope for a plain-Java sketch.