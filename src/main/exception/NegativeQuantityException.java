package main.exception;

public class NegativeQuantityException extends Exception {

    public NegativeQuantityException() {
        super();
    }

    public NegativeQuantityException(String message) {
        super(message);
    }
}