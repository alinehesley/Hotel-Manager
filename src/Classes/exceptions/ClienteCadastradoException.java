package Classes.exceptions;

public class ClienteCadastradoException extends ClienteException {
    public ClienteCadastradoException() {
        super("Cliente já cadastrado");
    }

    public ClienteCadastradoException(String message, Object... args) {
        super(message, args);
    }

    public ClienteCadastradoException(String message) {
        super(message);
    }
}
