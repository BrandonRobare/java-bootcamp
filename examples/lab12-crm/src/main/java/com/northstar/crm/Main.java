package com.northstar.crm;

import com.northstar.crm.entity.CustomerStatus;
import com.northstar.crm.service.CustomerService;

public class Main {
    public static void main(String[] args) {
        CustomerService svc = new CustomerService();
        svc.setCorrelationId("lab-request-001");

        System.out.println("create " + svc.createCustomer(
                "CUS-1001", "Amina Khan", "amina.khan@example.com", null, CustomerStatus.ACTIVE));
        System.out.println("create " + svc.createCustomer(
                "CUS-1002", "Ravi Singh", "ravi.singh@example.com", null, CustomerStatus.PROSPECT));
        System.out.println("get CUS-1001 -> " + svc.getCustomer(new String("CUS-1001")).getFullName());
        System.out.println("updateStatus CUS-1002 -> "
                + svc.updateStatus("CUS-1002", CustomerStatus.ACTIVE).getStatus());

        try {
            svc.createCustomer("CUS-1001", "Other", "x@example.com", null, CustomerStatus.PROSPECT);
        } catch (IllegalStateException e) {
            System.out.println("duplicate CUS-1001 -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        try {
            svc.getCustomer("CUS-9999");
        } catch (IllegalArgumentException e) {
            System.out.println("unknown CUS-9999 -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
