package com.northstar.crm;

import com.northstar.crm.entity.Customer;
import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.service.CustomerService;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Northstar customer service booting");
        CustomerService svc = new CustomerService();

        Customer c1 = new Customer("CUS-1001", "Amina Khan", "amina.khan@example.com",
                "555-0101", CustomerStatus.ACTIVE, LocalDateTime.now());
        Customer c2 = new Customer("CUS-1002", "Ravi Singh", "ravi.singh@example.com",
                "555-0102", CustomerStatus.PROSPECT, LocalDateTime.now());

        svc.addCustomer(c1);
        svc.addCustomer(c2);

        System.out.println("Amina: " + svc.findByCustomerId("CUS-1001"));
        System.out.println("Ravi before: " + svc.findByCustomerId("CUS-1002"));

        svc.updateStatus("CUS-1002", CustomerStatus.ACTIVE);
        System.out.println("After activation: " + svc.findByCustomerId("CUS-1002"));
    }
}
