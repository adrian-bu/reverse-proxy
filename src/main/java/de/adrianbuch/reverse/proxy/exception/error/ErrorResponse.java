package de.adrianbuch.reverse.proxy.exception.error;

/**
 * Lists constants for handling HTTP error response messages
 */
public class ErrorResponse {
    
    public static final String VALIDATION_ERROR_MESSAGE = "A validation error occurred, see details for more information.";
    public static final String BAD_REQUEST_GENERIC_MESSAGE = "Your input data is not valid or not in a consistent format. Please check it and try again. You can also check the response payload for more information.";

    private ErrorResponse() {
        //ignored
    }

    public static final class ErrorTypes {
        
        /**
         * 400 - Bad Request - validation problem.
         */
        public static final String BAD_REQUEST_VALIDATION = "validation_violation";

        /**
         * 400 - Bad Request - syntax problem.
         */
        public static final String BAD_REQUEST_SYNTAX = "bad_payload_syntax";

        /**
         * 401 - Unauthorized.
         */
        public static final String UNAUTHORIZED = "insufficient_credentials";

        /**
         * 403 - Forbidden.
         */
        public static final String FORBIDDEN = "insufficient_permissions";

        /**
         * 404 - Not Found - element.
         */
        public static final String NOT_FOUND_ELEMENT = "element_resource_non_existing";

        /**
         * 405 - Method Not Allowed.
         */
        public static final String METHOD_NOT_ALLOWED = "unsupported_method";

        /**
         * 406 - Not Acceptable.
         */
        public static final String NOT_ACCEPTABLE = "unsupported_response_content_type";

        /**
         * 409 - Conflict.
         */
        public static final String CONFLICT = "conflict_resource";

        /**
         * 413 - Request Entity Too Large.
         */
        public static final String REQUEST_ENTITY_TOO_LARGE = "bad_payload_size";

        /**
         * 414 - Request Uri Too Long.
         */
        public static final String REQUEST_URI_TOO_LONG = "uri_too_long";

        /**
         * 415 - Unsupported Media Type.
         */
        public static final String UNSUPPORTED_MEDIA_TYPE = "unsupported_request_content_type";

        /**
         * 500 - Internal Server Error.
         */
        public static final String INTERNAL_SERVER_ERROR = "internal_service_error";

        /**
         * 503 - Service Unavailable.
         */
        public static final String SERVICE_UNAVAILABLE = "service_temporarily_unavailable";

        private ErrorTypes() {
            // ignored
        }
    }

    public static final class ValidationErrorTypes {
        public static final String INVALID_FIELD = "invalid_field";
        private ValidationErrorTypes() {
            // ignored
        }
    }
}
