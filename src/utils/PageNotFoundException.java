package utils;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super();
    }

    public PageNotFoundException(String message) {
        super(message);
    }
}
