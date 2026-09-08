package com.northstar.crm.dto;

// REQUEST DTO = what comes IN from the client.
// Notice what is missing: no id (the client doesn't know it yet)
// and no status (the server decides that, not the caller).
public class CustomerRequest {

    private final String name;
    private final String email;

    public CustomerRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }
}
