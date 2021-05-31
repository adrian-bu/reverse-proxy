package de.adrianbuch.reverse.proxy.exception.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Utility class responsible for mapping error messages
 */
public class ErrorMessageMapper {
    
    private static final Map<Integer, String> topLevelErrorTypeMap = new HashMap<>();

    static {
        topLevelErrorTypeMap.put(400, ErrorResponse.ErrorTypes.BAD_REQUEST_SYNTAX);
        topLevelErrorTypeMap.put(401, ErrorResponse.ErrorTypes.UNAUTHORIZED);
        topLevelErrorTypeMap.put(403, ErrorResponse.ErrorTypes.FORBIDDEN);
        topLevelErrorTypeMap.put(404, ErrorResponse.ErrorTypes.NOT_FOUND_ELEMENT);
        topLevelErrorTypeMap.put(405, ErrorResponse.ErrorTypes.METHOD_NOT_ALLOWED);
        topLevelErrorTypeMap.put(406, ErrorResponse.ErrorTypes.NOT_ACCEPTABLE);
        topLevelErrorTypeMap.put(409, ErrorResponse.ErrorTypes.CONFLICT);
        topLevelErrorTypeMap.put(413, ErrorResponse.ErrorTypes.REQUEST_ENTITY_TOO_LARGE);
        topLevelErrorTypeMap.put(414, ErrorResponse.ErrorTypes.REQUEST_URI_TOO_LONG);
        topLevelErrorTypeMap.put(415, ErrorResponse.ErrorTypes.UNSUPPORTED_MEDIA_TYPE);
        topLevelErrorTypeMap.put(500, ErrorResponse.ErrorTypes.INTERNAL_SERVER_ERROR);
        topLevelErrorTypeMap.put(503, ErrorResponse.ErrorTypes.SERVICE_UNAVAILABLE);
    }

    private ErrorMessageMapper() {
        // ignored
    }

    public static ErrorMessage map(final HttpStatus status, final String message) {
        return map(status, message, null, null);
    }

    public static ErrorMessage map(final HttpStatus status, final String message, final Throwable bCause) {
        return map(status, message, null, bCause);
    }

    public static ErrorMessage map(final HttpStatus status, final String message, final String errorType) {
        return map(status, message, errorType, null);
    }

    public static ErrorMessage map(final HttpStatus status, final String message, final String errorType, final Throwable bCause) {
        if (status != null && (status.is1xxInformational() || status.is2xxSuccessful() || status.is3xxRedirection())) {
            throw new IllegalStateException("Please use the ErrorMessageMapper only to return 4xx or 5xx error messages.");
        } else {
            final var errorMessage = new ErrorMessage();
            errorMessage.setMessage(message);
            errorMessage.setStatus(status == null ? 0 : status.value());
            errorMessage.setType(errorType == null ? getTopLevelErrorTypeFromStatus(status) : errorType);
            final ArrayList<ErrorMessage.Detail> details = new ArrayList<>();
            for (Throwable cause = bCause; cause != null; cause = cause.getCause()) {
                final var detail = new ErrorMessage.Detail();
                detail.setMessage("caused by: " + cause.getMessage());
                details.add(detail);
            }
            errorMessage.setDetails(details);
            return errorMessage;
        }
    }

    private static String getTopLevelErrorTypeFromStatus(final HttpStatus status) {
        if (status != null) {
            final String errorType = topLevelErrorTypeMap.get(status.value());
            return errorType == null ? "unsupported_status_code" : errorType;
        }
        return "unsupported_status_code";
    }
}
