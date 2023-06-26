package Classes.exceptions;

public class QuartoIndisponivelException extends QuartoException {
    public QuartoIndisponivelException(int n) {
        super("Quarto %d está indisponível para locação", n);
    }

    public QuartoIndisponivelException(String message, Object... args) {
        super(message, args);
    }

    public QuartoIndisponivelException(String message) {
        super(message);
    }
}
