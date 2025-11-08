package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterInvoiceTest {

    @Test
    @DisplayName("Before refactoring: FilterInvoice uses real DB and can't be isolated")
    void testLowValueInvoices_RealDatabase() {
        FilterInvoice service = new FilterInvoice();

        assertDoesNotThrow(() -> service.lowValueInvoices());
    }

    @Test
    @DisplayName("After refactoring: FilterInvoice can be tested with a stub DAO and no database")
    void testLowValueInvoices_WithStubDAO() {
        QueryInvoicesDAO stubDao = mock(QueryInvoicesDAO.class);

        List<Invoice> fakeInvoices = List.of(
                new Invoice("A", 50),
                new Invoice("B", 150),
                new Invoice("C", 75)
        );
        when(stubDao.all()).thenReturn(fakeInvoices);

        FilterInvoice service = new FilterInvoice(stubDao);

        List<Invoice> result = service.lowValueInvoices();

        assertEquals(List.of(
                new Invoice("A", 50),
                new Invoice("C", 75)
        ), result);
    }
}