package es.atz.software.products.exception;

public class BaseNotFoundException extends RuntimeException {

    public BaseNotFoundException() {
        super();
    }

    public BaseNotFoundException(String message) {
        super(message);
    }

    public BaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
