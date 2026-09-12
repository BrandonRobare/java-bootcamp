package com.northstar.crm;

import com.northstar.crm.dto.CustomerRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static com.northstar.crm.entity.CustomerStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;

class CustomerRequestValidationTest {
    static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validAminaRequestPasses() {
        // DONE: build valid DTO for CUS-1001; assert violations empty
        CustomerRequestDTO request = new CustomerRequestDTO(
                "CUS-1001",
                "Amina Khan",
                "amina.khan@example.com",
                "ACTIVE");
        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void invalidEmailFails() {
        // DONE: bad email → assert violations mention email
        CustomerRequestDTO request = new CustomerRequestDTO(
                "CUS-1001",
                "Amina Khan",
                "badEmail",
                "ACTIVE");
        assertTrue(validator.validate(request).stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void blankNameFails() {
        // DONE: blank fullName → assert violation
        CustomerRequestDTO request = new CustomerRequestDTO(
                "CUS-1001",
                " ",
                "amina.khan@example.com",
                "ACTIVE");
        assertFalse(validator.validate(request).isEmpty());
    }
}
