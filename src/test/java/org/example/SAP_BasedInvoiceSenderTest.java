package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

class SAP_BasedInvoiceSenderTest {

    @Test
    @DisplayName("Sends all low-value invoices when available")
    void testWhenLowInvoicesSent() {
        // Arrange: mock dependencies
        FilterInvoice filter = Mockito.mock(FilterInvoice.class);
        SAP sap = Mockito.mock(SAP.class);

        Invoice inv1 = new Invoice("Alice", 50);
        Invoice inv2 = new Invoice("Bob", 75);

        // Stub filter to return invoices that should be sent
        when(filter.lowValueInvoices()).thenReturn(List.of(inv1, inv2));

        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(filter, sap);

        // Act
        sender.sendLowValuedInvoices();

        // Assert: verify sap.send() is called for each invoice
        verify(sap, times(1)).send(inv1);
        verify(sap, times(1)).send(inv2);
    }

    @Test
    @DisplayName("Does nothing when there are no low-value invoices")
    void testWhenNoInvoices() {
        // Arrange
        FilterInvoice filter = Mockito.mock(FilterInvoice.class);
        SAP sap = Mockito.mock(SAP.class);

        // Stub filter to return empty list
        when(filter.lowValueInvoices()).thenReturn(List.of());

        SAP_BasedInvoiceSender sender = new SAP_BasedInvoiceSender(filter, sap);

        // Act
        sender.sendLowValuedInvoices();

        // Assert: verify no SAP sends happen
        verify(sap, never()).send(any());
    }
}