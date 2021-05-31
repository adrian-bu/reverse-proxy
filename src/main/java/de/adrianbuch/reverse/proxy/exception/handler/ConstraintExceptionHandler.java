package de.adrianbuch.reverse.proxy.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.adrianbuch.reverse.proxy.exception.error.ErrorMessage;
import de.adrianbuch.reverse.proxy.exception.error.ErrorMessageMapper;
import de.adrianbuch.reverse.proxy.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.LOWEST_PRECEDENCE - 9)
@ControllerAdvice
@Slf4j
public class ConstraintExceptionHandler {
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleMissingServletRequestParameterException(final MissingServletRequestParameterException exception) {
        final var message = "The required parameter is missing, see details for more information.";
        log.error(message, exception);
        return ErrorMessageMapper.map(HttpStatus.BAD_REQUEST, message, ErrorResponse.ErrorTypes.BAD_REQUEST_SYNTAX, exception);
    }
}
