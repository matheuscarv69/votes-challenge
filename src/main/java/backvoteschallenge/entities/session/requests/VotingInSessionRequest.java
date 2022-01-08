package backvoteschallenge.entities.session.requests;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.vote.entity.TypeVote;
import backvoteschallenge.entities.vote.entity.Vote;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class VotingInSessionRequest {

    @ApiModelProperty(value = "Id do Associado", position = 2, required = true)
    @NotNull
    @Positive
    private Long associateId;

    @ApiModelProperty(value = "Voto", position = 3, example = "Sim | Nao", required = true)
    @NotNull
    private TypeVote vote;

    public VotingInSessionRequest(Long associateId, TypeVote vote) {
        this.associateId = associateId;
        this.vote = vote;
    }

    public Vote toModel(Associate associate) {
        return new Vote(vote, associate);
    }

    public VotingInSessionRequest() {
    }

    public Long getAssociateId() {
        return associateId;
    }

    public void setAssociateId(Long associateId) {
        this.associateId = associateId;
    }

    public TypeVote getVote() {
        return vote;
    }

    public void setVote(TypeVote vote) {
        this.vote = vote;
    }
}
