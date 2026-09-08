package com.northstar.crm.controller;

import com.northstar.crm.dto.CustomerRequest;
import com.northstar.crm.dto.CustomerResponse;
import com.northstar.crm.service.CustomerService;

// CONTROLLER = the front door. Take the request, hand it to the service, pass the answer back.
// That is the entire job. If a controller ever grows an if-statement about business
// rules or a SQL string, the rule belongs one layer down.
//
// "Thin controller" is not a style preference -- it is what lets you test the business
// logic without starting a web server.
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // Later this method gets @PostMapping and returns ResponseEntity<CustomerResponse>.
    // The body stays this exact one line. The annotations are the only thing added.
    public CustomerResponse create(CustomerRequest request) {
        return service.createCustomer(request);
    }

    // Later: @GetMapping("/{id}")
    public CustomerResponse getById(Long id) {
        return service.getCustomer(id);
    }
}
