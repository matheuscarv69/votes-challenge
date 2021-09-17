package backvoteschallenge.config.exception;


public class OrderNotFoundException extends RuntimeException {

    private static final String defaultMessage = "Não foi encontrado nenhuma pauta com o ID informado.";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        super(defaultMessage);
    }

}
