package com.northstar.crm.api;

import com.northstar.crm.dto.CustomerRequestDTO;
import com.northstar.crm.dto.CustomerResponseDTO;
import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.exception.CustomerNotFoundException;
import com.northstar.crm.mapper.CustomerMapper;
import com.northstar.crm.service.CustomerService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * API edge: validate → map → service → response DTO.
 * Correlation: lab-request-001 on failures.
 *
 * FUTURE (Spring Boot): this whole class becomes a @RestController.
 * Each public method becomes an endpoint (@PostMapping / @GetMapping).
 * Callers stop invoking Java methods and send HTTP requests instead.
 */
public class CustomerApiFacade {
    private final CustomerService service;

    // FUTURE: removed. Spring owns the Validator bean; nothing here builds one.
    private final Validator validator;

    // FUTURE: constructor stays, but Spring calls it (@Service injected via constructor).
    // Main never does `new CustomerApiFacade(...)` again.
    public CustomerApiFacade(CustomerService service) {
        this.service = service;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // FUTURE: signature becomes
    //   @PostMapping("/customers")
    //   public CustomerResponseDTO create(@Valid @RequestBody CustomerRequestDTO request)
    // @Valid triggers the same DTO annotations; correlationId comes from a header/filter, not a parameter.
    public CustomerResponseDTO create(CustomerRequestDTO request, String correlationId) {
        // FUTURE: this call disappears. @Valid runs validation before the method body executes.
        validateOrThrow(request, correlationId);

        // STAYS: business call and DTO mapping are the same in Spring.
        Customer saved = service.createCustomer(
                request.getCustomerId(),
                request.getFullName(),
                request.getEmail(),
                null,
                CustomerStatus.valueOf(request.getStatus()));
        return CustomerMapper.toResponse(saved);
    }

    // FUTURE: @GetMapping("/customers/{id}") with @PathVariable String customerId.
    // Not-found exception → 404 via @ControllerAdvice instead of a message string.
    public CustomerResponseDTO get(String customerId, String correlationId) {
        // DONE: getCustomer → toResponse; include correlationId in not-found message/log
        Customer customer = service.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("[" + correlationId + "] " + customerId));
        return CustomerMapper.toResponse(customer);
    }


    // FUTURE: removed entirely. Spring throws MethodArgumentNotValidException on @Valid failure;
    // a @ControllerAdvice @ExceptionHandler turns it into an HTTP 400 with the field messages.
    // The correlation ID moves to MDC / a request filter so every log line carries it automatically.
    private void validateOrThrow(CustomerRequestDTO request, String correlationId) {
        Set<ConstraintViolation<CustomerRequestDTO>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String msg = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("; "));
            // DONE: log correlationId + msg (no secrets)
            System.err.println("[" + correlationId + "] validation failed: " + msg);
            throw new IllegalArgumentException("[" + correlationId + "] " + msg);
        }
    }
}
