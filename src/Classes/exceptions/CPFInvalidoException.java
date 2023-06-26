package Classes.exceptions;

public class CPFInvalidoException extends ClienteException {
    public CPFInvalidoException(String cpf) {
        super("O CPF %s é inválido", cpf);
    }

    public CPFInvalidoException(String message, Object... args) {
        super(message, args);
    }
}
