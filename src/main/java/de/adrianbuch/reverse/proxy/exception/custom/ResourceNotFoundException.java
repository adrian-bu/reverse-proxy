package de.adrianbuch.reverse.proxy.exception.custom;

/**
 * Generic exception which says that a given resource (e.g. a detection point or dedocy) can't be found
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("There are no records matched by the request arguments.");
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
