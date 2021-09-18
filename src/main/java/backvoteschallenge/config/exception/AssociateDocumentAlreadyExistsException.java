package backvoteschallenge.config.exception;


public class AssociateDocumentAlreadyExistsException extends RuntimeException {

    private static final String defaultMessage = "Foi encontrado um Associado com o CPF informado.";

    public AssociateDocumentAlreadyExistsException() {
        super(defaultMessage);
    }

}
