package com.northstar.crm.dto;

public class CustomerRequest {
    // DONE: name + email fields and constructor/getters (no id/status required on request)
    private final String name;
    private final String email;

    public CustomerRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}
