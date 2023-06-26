package Classes.exceptions;

// A classe ExceptionBase é uma classe base para as exceções customizadas do programa.
// A diferença é que essa classe aceita argumentos variáveis, usando a função String.format para formatar a mensagem.
public class ExceptionBase extends Exception {
    public ExceptionBase() {
        super();
    }

    public ExceptionBase(String message) {
        super(message);
    }

    public ExceptionBase(String message, Object... args) {
        super(String.format(message, args));
    }
}
