package com.northstar.crm.service;

import com.northstar.crm.entity.CustomerStatus;

/** Extracted collaborator for Lab 11 — verify with Mockito. */
public interface CustomerNotifier {
    void notifyStatusChange(String customerId, CustomerStatus oldStatus, CustomerStatus newStatus);
}
