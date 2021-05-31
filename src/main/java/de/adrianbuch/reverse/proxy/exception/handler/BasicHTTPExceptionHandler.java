package de.adrianbuch.reverse.proxy.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.adrianbuch.reverse.proxy.exception.error.ErrorMessage;
import de.adrianbuch.reverse.proxy.exception.error.ErrorMessageMapper;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.LOWEST_PRECEDENCE - 7)
@ControllerAdvice
@Slf4j
public class BasicHTTPExceptionHandler {
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorMessage handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorMessageMapper.map(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ErrorMessage handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException exception) {
        final var message = "The requested media type is not supported.";
        log.error(message, exception);
        return ErrorMessageMapper.map(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message, exception);
    }
}
