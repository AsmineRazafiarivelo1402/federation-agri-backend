package org.hei.federationagribackend.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}