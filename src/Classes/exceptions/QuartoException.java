package Classes.exceptions;

public class QuartoException extends ExceptionBase {
    public QuartoException() {
        super();
    }

    public QuartoException(String message, Object... args) {
        super(message, args);
    }

    public QuartoException(String message) {
        super(message);
    }
}
