package de.adrianbuch.reverse.proxy.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.adrianbuch.reverse.proxy.exception.custom.ResourceNotFoundException;
import de.adrianbuch.reverse.proxy.exception.error.ErrorMessage;
import de.adrianbuch.reverse.proxy.exception.error.ErrorMessageMapper;
import de.adrianbuch.reverse.proxy.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.LOWEST_PRECEDENCE - 8)
@ControllerAdvice
@Slf4j
public class DatabaseExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage handleFailedToLoadResourceException(final ResourceNotFoundException exception) {
        log.info("Generic exception handler invoked for exception class '{}'.", exception.getClass().toString());
        log.error(exception.getMessage(), exception);
        return ErrorMessageMapper.map(HttpStatus.NOT_FOUND, exception.getMessage(), ErrorResponse.ErrorTypes.NOT_FOUND_ELEMENT);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage handleOptimisticLockingFailureException(final OptimisticLockingFailureException exception) {
        log.error(exception.getMessage());
        return ErrorMessageMapper.map(HttpStatus.CONFLICT, "The resource cannot be updated. Has it been modified meanwhile?", ErrorResponse.ErrorTypes.CONFLICT);
    }
}
