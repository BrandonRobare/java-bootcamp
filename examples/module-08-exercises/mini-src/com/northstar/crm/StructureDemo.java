package com.northstar.crm;

import com.northstar.crm.dto.CustomerRequest;
import com.northstar.crm.dto.CustomerResponse;
import com.northstar.crm.entity.Customer;

public class StructureDemo {
    public static void main(String[] args) {
        CustomerRequest request = new CustomerRequest("Amina Khan", "amina@example.test");

        // DONE: build Customer entity from request (assign an id + ACTIVE status)
        Customer entity = new Customer("CUS-1001", request.getName(), "ACTIVE");

        // DONE: map entity → CustomerResponse
        CustomerResponse response = new CustomerResponse(
                entity.getId(),
                entity.getName(),
                entity.getStatus());

        System.out.println(response.summary());
    }
}
