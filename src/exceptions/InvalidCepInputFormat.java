package exceptions;

public class InvalidCepInputFormat extends RuntimeException {
    public InvalidCepInputFormat() {
        super("CEP com formato não aceito pelo sistema");
    }
}
