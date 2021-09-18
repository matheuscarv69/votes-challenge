package backvoteschallenge.config.exception;


public class AssociateUnableToVoteException extends RuntimeException {

    private static final String defaultMessage = "Associado não esta habilitado à votar.";

    public AssociateUnableToVoteException() {
        super(defaultMessage);
    }

}
