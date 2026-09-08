package com.northstar.crm.repository;

import com.northstar.crm.entity.Customer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// THE DATABASE, standing in as a HashMap so this example runs with no server.
// The Map is the table. The key is the primary key. The counter is AUTO_INCREMENT.
//
// Swap this one class for a Spring Data JPA repository against real PostgreSQL
// and every other file in this project stays exactly the same. That is the test
// of whether your layers are actually separated.
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<Long, Customer> table = new HashMap<>();
    private long nextId = 1;

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(nextId++);   // the database assigns the id, not your code
        }
        table.put(customer.getId(), customer);
        System.out.println("      [db]   INSERT INTO customers -> id " + customer.getId());
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        System.out.println("      [db]   SELECT * FROM customers WHERE id = " + id);
        return Optional.ofNullable(table.get(id));
    }
}
