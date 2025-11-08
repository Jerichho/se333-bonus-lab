package org.example;

public class FailToSendSAPInvoiceException extends RuntimeException {
    public FailToSendSAPInvoiceException(String message, Throwable cause) {
        super(message, cause);
    }
}