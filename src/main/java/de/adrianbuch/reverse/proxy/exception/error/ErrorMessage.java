package de.adrianbuch.reverse.proxy.exception.error;

import java.net.URI;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Exception handling error message POJO
 */
@Getter
@Setter
public class ErrorMessage {

    /**
     * HTTP error status code
     */
    public static final String STATUS = "status";

    /**
     * Classification of the error type
     */
    public static final String TYPE = "type";

    /**
     * Constraint for TYPE value
     */
    public static final Pattern TYPE_PATTERN = Pattern.compile("[a-z]+[a-z_]*[a-z]+");

    /**
     * Descriptive error message for debugging
     */
    public static final String MESSAGE = "message";

    /**
     * Link to documentation to investigate further and finding support
     */
    public static final String MORE_INFO = "moreInfo";

    /**
     * List of problems causing the error
     */
    public static final String DETAILS = "details";

    /**
     * Optional code that can be used from a consuming service
     */
    public static final String CODE = "code";

    /**
     * Used in conjunction with code to allow the insertion of parameters
     */
    public static final String PARAMETERS = "parameters";

    private Integer status;
    private String type;
    private URI moreInfo;
    private String code;
    private String[] parameters;
    private String message;
    private List<Detail> details;

    /**
     * Detailed error schema for API specific problems
     */
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Detail {

        /**
         * Attribute key for a bean notation expression specifying the element in request data causing the error, eg
         * product.variants[3].name. This can be empty if violation was not field specific.
         */
        public static final String FIELD = "field";

        /**
         * Classification of the error detail type, lower case with underscore (e.g. missing_value)
         * This value must be always interpreted in context of the general error type.
         */
        public static final String TYPE = "type";

        /**
         * Regular expression for validation of TYPE value
         */
        public static final Pattern TYPE_PATTERN = Pattern.compile("[a-z]+[a-z_]*[a-z]+");

        /**
         * Descriptive error detail message for debugging
         */
        public static final String MESSAGE = "message";

        /**
         * Link to documentation to investigate further and finding support
         */
        public static final String MORE_INFO = "moreInfo";

        /**
         * Key for an optional code that can be used from a consuming service
         */
        public static final String CODE = "code";

        /**
         * Used in conjunction with code to allow the insertion of parameters
         */
        public static final String PARAMETERS = "parameters";

        private String field;
        private String type;
        private String message;
        private String code;
        private String[] parameters;
        private URI moreInfo;
    }
}
