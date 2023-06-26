package Classes.exceptions;

// A classe RuntimeExceptionBase é uma classe base para as exceções customizadas do programa.
// A diferença dessa classe para a ExceptionBase é que essa classe herda de RuntimeException, e não de Exception.
public class RuntimeExceptionBase extends RuntimeException {
    public RuntimeExceptionBase() {
        super();
    }

    public RuntimeExceptionBase(String message) {
        super(message);
    }

    public RuntimeExceptionBase(String message, Object... args) {
        super(String.format(message, args));
    }
}
