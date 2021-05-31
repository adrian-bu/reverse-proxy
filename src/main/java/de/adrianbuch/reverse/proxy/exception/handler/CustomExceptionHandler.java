package de.adrianbuch.reverse.proxy.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.adrianbuch.reverse.proxy.exception.error.ErrorMessage;
import de.adrianbuch.reverse.proxy.exception.error.ErrorMessageMapper;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.LOWEST_PRECEDENCE - 1)
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage handleAnyException(Exception exception) {
        log.info("Generic exception handler invoked for exception class '{}'.", exception.getClass().toString());
        String errorMessage = exception.getMessage();
        log.error(errorMessage, exception);
        return ErrorMessageMapper.map(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}
