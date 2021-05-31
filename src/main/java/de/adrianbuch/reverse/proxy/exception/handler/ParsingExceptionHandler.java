package de.adrianbuch.reverse.proxy.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import de.adrianbuch.reverse.proxy.exception.error.ErrorMessage;
import de.adrianbuch.reverse.proxy.exception.error.ErrorMessageMapper;
import de.adrianbuch.reverse.proxy.exception.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import static de.adrianbuch.reverse.proxy.exception.error.ErrorResponse.VALIDATION_ERROR_MESSAGE;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.LOWEST_PRECEDENCE - 10)
@ControllerAdvice
@Slf4j
public class ParsingExceptionHandler {

    @ExceptionHandler(InvalidDefinitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleMethodInvalidDefinitionException(final InvalidDefinitionException exception) {
        final String message = VALIDATION_ERROR_MESSAGE;
        final var errorMessage = ErrorMessageMapper.map(HttpStatus.BAD_REQUEST, message, ErrorResponse.ErrorTypes.BAD_REQUEST_VALIDATION, exception.getCause());
        log.error(message, exception);
        return errorMessage;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        log.error(VALIDATION_ERROR_MESSAGE, exception);
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final var errorMessage = ErrorMessageMapper.map(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_MESSAGE, ErrorResponse.ErrorTypes.BAD_REQUEST_VALIDATION);
        errorMessage.setDetails(mapFieldErrors(fieldErrors));
        return errorMessage;
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleServletRequestBindingException(final ServletRequestBindingException exception) {
        log.error(exception.getMessage());
        return ErrorMessageMapper.map(HttpStatus.BAD_REQUEST, ErrorResponse.BAD_REQUEST_GENERIC_MESSAGE, ErrorResponse.ErrorTypes.BAD_REQUEST_SYNTAX);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        String details = null;
        final var message = "Problem parsing a JSON attribute, see details for more information.";
        if (exception.getMostSpecificCause().getClass() == JsonParseException.class) {
            details = exception.getMostSpecificCause().getMessage().replace("\n", "").replace("('", "'").replaceAll("Source: \\(.*?\\).*?; ", "").replaceAll(" \\(code.*?\\)\\)", "").replaceAll("; nested exception is .*? at \\[", " at [");
        }
        log.error(message, exception);
        return ErrorMessageMapper.map(HttpStatus.BAD_REQUEST, message, ErrorResponse.ErrorTypes.BAD_REQUEST_SYNTAX, new Exception(details));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage());
        return ErrorMessageMapper.map(HttpStatus.BAD_REQUEST, "A validation error occurred, see details for more information.", ErrorResponse.ErrorTypes.BAD_REQUEST_SYNTAX);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage handleTypeMismatchException(final TypeMismatchException exception) {
        log.error(exception.getMessage());
        return ErrorMessageMapper.map(HttpStatus.BAD_REQUEST, exception.getValue() + " is not valid.", ErrorResponse.ErrorTypes.BAD_REQUEST_SYNTAX);
    }

    private List<ErrorMessage.Detail> mapFieldErrors(final List<FieldError> fieldErrors) {
        return fieldErrors.stream().map(this::processFieldError).collect(Collectors.toList());
    }

    private ErrorMessage.Detail processFieldError(FieldError fieldError) {
        final ErrorMessage.Detail detail = new ErrorMessage.Detail();
        detail.setField(fieldError.getField());
        detail.setType(ErrorResponse.ValidationErrorTypes.INVALID_FIELD);
        detail.setMessage(fieldError.getDefaultMessage());
        return detail;
    }
}
