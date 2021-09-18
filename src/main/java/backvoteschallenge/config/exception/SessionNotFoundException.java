package backvoteschallenge.config.exception;


public class SessionNotFoundException extends RuntimeException {

    private static final String defaultMessage = "Não foi encontrado nenhuma sessão com o ID informado.";

    public SessionNotFoundException() {
        super(defaultMessage);
    }

}
