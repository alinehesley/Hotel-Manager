package Classes.exceptions;

public class ClienteException extends ExceptionBase {
    public ClienteException() {
        super();
    }

    public ClienteException(String message, Object... args) {
        super(message, args);
    }

    public ClienteException(String message) {
        super(message);
    }
}
