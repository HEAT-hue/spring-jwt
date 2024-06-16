package com.jwt.demo.util;

import com.jwt.demo.exception.NotFoundException;
import com.jwt.demo.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * Add your Exception Handlers here
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // User details not found exception
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions(HttpServletRequest request, NotFoundException ex) {
        return createHttpErrorInfo(NOT_FOUND, null, ex);
    }

    // User already exists exception
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public @ResponseBody HttpErrorInfo handleUserAlreadyExistsException(HttpServletRequest request, UserAlreadyExistsException ex) {
        return createHttpErrorInfo(CONFLICT, request, ex);
    }

    // Error accessing database || Completing database operation
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public @ResponseBody HttpErrorInfo handleDataAccessException(HttpServletRequest request, DataAccessException ex) {
        System.out.println(ex.getMessage());
        System.out.println("Data access exception thrown");
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex);
    }

    // Server runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public @ResponseBody HttpErrorInfo handleAllExceptions(HttpServletRequest request, Exception ex) {
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex);
    }

    // Authentication exception
    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class,
            AccountExpiredException.class,
            LockedException.class,
            DisabledException.class,
            CredentialsExpiredException.class})
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody HttpErrorInfo handleAuthException(HttpServletRequest request, Exception ex) {
        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, HttpServletRequest request, Exception ex) {
        final String path = request.getRequestURI();
        final String message = ex.getMessage();
        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }
}
