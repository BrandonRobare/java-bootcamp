package com.northstar.crm.entity;

public class Customer {
    // DONE: declare three final String fields — id, name, status
    private final String id;
    private final String name;
    private final String status;

    public Customer(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getStatus() { return status; }
}
