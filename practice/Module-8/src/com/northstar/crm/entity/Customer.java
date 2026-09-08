package com.northstar.crm.entity;

// ENTITY = the DATABASE's shape. One Customer object = one row in a customers table.
// It has an id because the database assigned one.
// Later this same class gets @Entity / @Table / @Id on top. Nothing else changes.
public class Customer {

    private Long id;          // null until the database saves it
    private String name;
    private String email;
    private String status;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
