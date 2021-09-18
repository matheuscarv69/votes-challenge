package backvoteschallenge.config.exception;


public class AssociateNotFoundException extends RuntimeException {

    private static final String defaultMessage = "Não foi encontrado nenhum associado com o ID informado.";

    public AssociateNotFoundException() {
        super(defaultMessage);
    }

}
