package com.northstar.crm;

import com.northstar.crm.api.CustomerApiFacade;
import com.northstar.crm.dto.CustomerRequestDTO;
import com.northstar.crm.dto.CustomerResponseDTO;
import com.northstar.crm.exception.CustomerNotFoundException;
import com.northstar.crm.service.CustomerService;

public class Main {
    private static final String CORRELATION_ID = "lab-request-001";

    public static void main(String[] args) {
        CustomerApiFacade api = new CustomerApiFacade(new CustomerService());

        // DONE: create CUS-1001 / CUS-1002 via DTOs; print CustomerResponseDTO only
        print(api.create(new CustomerRequestDTO(
                "CUS-1001", "Amina Khan", "amina.khan@example.com", "ACTIVE"), CORRELATION_ID));
        print(api.create(new CustomerRequestDTO(
                "CUS-1002", "Ravi Singh", "ravi.singh@example.com", "PROSPECT"), CORRELATION_ID));
        print(api.get("CUS-1001", CORRELATION_ID));

        // DONE: attempt invalid email; show correlation lab-request-001 in failure
        try {
            api.create(new CustomerRequestDTO(
                    "CUS-1003", "Bad Email", "not-an-email", "ACTIVE"), CORRELATION_ID);
        } catch (IllegalArgumentException e) {
            System.out.println("REJECTED: " + e.getMessage());
        }

        try {
            api.get("CUS-9999", CORRELATION_ID);
        } catch (CustomerNotFoundException e) {
            System.out.println("REJECTED: " + e.getMessage());
        }
    }

    private static void print(CustomerResponseDTO dto) {
        System.out.println("OK: " + dto.getCustomerId() + " | " + dto.getFullName()
                + " | " + dto.getEmail() + " | " + dto.getStatus() + " | " + dto.getCreatedAt());
    }
}
