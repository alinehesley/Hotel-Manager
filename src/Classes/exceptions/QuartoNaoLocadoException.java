package Classes.exceptions;

public class QuartoNaoLocadoException extends QuartoException {
    public QuartoNaoLocadoException(int n) {
        super("Quarto %d não está locado", n);
    }

    public QuartoNaoLocadoException(String message, Object... args) {
        super(message, args);
    }

    public QuartoNaoLocadoException(String message) {
        super(message);
    }
}
