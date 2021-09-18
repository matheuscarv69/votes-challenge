package backvoteschallenge.config.exception;


public class AssociateAlreadyVotedException extends RuntimeException {

    private static final String defaultMessage = "O Associado informado já votou nesta Pauta.";

    public AssociateAlreadyVotedException() {
        super(defaultMessage);
    }

}
