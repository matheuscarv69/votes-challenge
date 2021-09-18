package backvoteschallenge.core.clients.cpfCheck.response;

public class CpfCheckResponse {

    private StatusPossibleVote status;

    public CpfCheckResponse(StatusPossibleVote status) {
        this.status = status;
    }

    public StatusPossibleVote getStatus() {
        return status;
    }

}
