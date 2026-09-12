# Lab 14 - Fill ValidatorFactory TODOs

## Step 1 - Copy TODOs

Bootstrap: `ValidatorFactory factory = Validation.buildDefaultValidatorFactory();`
`Validator validator = factory.getValidator();`
Invalid blank name: expect at least 1 violation (`fullName: must not be blank`; a `"   "` name may also trip `@Size(min = 2)` after trim, so 1 or 2)
Invalid status TYPO (`"ACTIVATED"`): expect 0 violations from `@NotBlank` alone; the mapper's `CustomerStatus.valueOf` throws `IllegalArgumentException`. Add `@Pattern` if it should be a validation failure instead
Valid Amina ACTIVE sketch (`CUS-1001`, `Amina Khan`, `amina.khan@example.com`, `ACTIVE`): expect 0 violations
Spring `@Valid` in this pre-lab? no

## Step 2 - Fill blanks

Both imports are `jakarta.validation.*`, not `javax.validation.*`. The starter test builds the validator once in `@BeforeAll` and each test calls `validator.validate(dto)` and asserts on the returned `Set<ConstraintViolation<CustomerRequestDTO>>`. To check which field failed: `v.getPropertyPath().toString()` equals `"email"`, and `v.getMessage()` is the text.

Predict: `validator.validate(dto)` does not mutate the DTO. It reads the fields, returns a new set of violations, and leaves the object as it was. That is why the facade can log the violations and still safely throw.

Debug: `NoProviderFoundException` means the API jar is on the classpath but no implementation is. The pom needs all three: `jakarta.validation-api` 3.1.0 (the annotations and `Validation` class), `hibernate-validator` 8.0.2.Final (the provider that actually validates), and `org.glassfish.expressly` 5.0.0 (the EL engine Hibernate Validator needs to render `{min}`/`{max}` in messages). Missing the last one shows up as a different error about `jakarta.el`.

## Step 3 - Invalid cases list

1. Blank `fullName`: `@NotBlank` fires, 1 violation on `fullName`.
2. Malformed email `ravi.singh`: `@Email` fires, 1 violation on `email`.
3. Null `customerId` on activate: `@NotBlank` fires, 1 violation on `customerId`. Unknown `CUS-9999` is not on this list; it passes validation and fails in the service.

## Scope

Pre-lab only.
