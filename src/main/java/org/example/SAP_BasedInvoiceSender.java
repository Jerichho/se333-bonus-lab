package org.example;

import java.util.List;

// Class responsible for sending low-valued invoices to the SAP system
public class SAP_BasedInvoiceSender {

    private final FilterInvoice filter;  // Dependency for filtering invoices
    private final SAP sap;  // Dependency for sending invoices to the SAP system

    // Constructor that uses dependency injection to initialize the filter and sap objects
    public SAP_BasedInvoiceSender(FilterInvoice filter, SAP sap) {
        this.filter = filter;
        this.sap = sap;
    }

    // Method to send all low-valued invoices to the SAP system
    public List<Invoice> sendLowValuedInvoices() {
        List<Invoice> lowValuedInvoices = filter.lowValueInvoices();
        List<Invoice> failed = new java.util.ArrayList<>();

        for (Invoice invoice : lowValuedInvoices) {
            try {
                sap.send(invoice);
            } catch (Exception e) {
                failed.add(invoice);
            }
        }

        return failed;
    }

    }
