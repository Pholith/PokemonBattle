package utils;

public class UnkownPageException extends RuntimeException {
    public UnkownPageException() {
        super();
    }

    public UnkownPageException(String message) {
        super(message);
    }
}
