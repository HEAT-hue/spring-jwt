package com.jwt.demo.exception;

public class JwtTokenException extends RuntimeException {
    public JwtTokenException() {
    }

    public JwtTokenException(String message) {
        super(message);
    }

    public JwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenException(Throwable cause) {
        super(cause);
    }
}
