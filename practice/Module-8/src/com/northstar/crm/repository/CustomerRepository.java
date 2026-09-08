package com.northstar.crm.repository;

import com.northstar.crm.entity.Customer;
import java.util.Optional;

// REPOSITORY = the door to the database. Nothing above this line knows about storage.
// It is an INTERFACE so the storage behind it can be swapped without touching
// the service or the controller. That swap is the payoff of the whole layer.
//
// Notice: this file imports the entity only. It never imports the service or the
// controller. Dependencies point ONE way: controller -> service -> repository.
public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(Long id);
}
