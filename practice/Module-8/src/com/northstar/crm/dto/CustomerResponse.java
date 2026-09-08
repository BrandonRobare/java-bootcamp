package com.northstar.crm.dto;

// RESPONSE DTO = what goes OUT to the client.
// Email is deliberately left out. This is the whole point of a DTO:
// you choose what leaves the building. Never return the entity directly.
public class CustomerResponse {

    private final Long customerId;
    private final String name;
    private final String status;

    public CustomerResponse(Long customerId, String name, String status) {
        this.customerId = customerId;
        this.name = name;
        this.status = status;
    }

    public Long getCustomerId() { return customerId; }

    public String getName() { return name; }

    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "CustomerResponse{id=" + customerId + ", name=" + name + ", status=" + status + "}";
    }
}
