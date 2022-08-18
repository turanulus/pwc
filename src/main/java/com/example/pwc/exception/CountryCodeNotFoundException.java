package com.example.pwc.exception;

public class CountryCodeNotFoundException extends RuntimeException {
    public CountryCodeNotFoundException(String message) {
        super(message);
    }
}
