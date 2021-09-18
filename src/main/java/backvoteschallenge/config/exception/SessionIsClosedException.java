package backvoteschallenge.config.exception;


public class SessionIsClosedException extends RuntimeException {

    private static final String defaultMessage = "A sessão informada já está fechada para votações, aguarde a próxima.";

    public SessionIsClosedException(String message) {
        super(message);
    }

    public SessionIsClosedException() {
        super(defaultMessage);
    }

}
