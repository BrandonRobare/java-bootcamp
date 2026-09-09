package com.northstar.crm.config;

/** Application wiring placeholders — Spring @Configuration arrives later. */
public class AppConfig {
    // DONE: document future bean wiring in a comment; no framework code yet

    /*
     later, @Configuration + @Bean methods wire CustomerRepository into
     CustomerService and CustomerService into CustomerController.
     Datasource and profile settings (dev / test / prod) also live here.
     No business logic — config assembles objects, it never decides anything
     */
}
