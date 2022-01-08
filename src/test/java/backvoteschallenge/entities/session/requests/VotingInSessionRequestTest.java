package backvoteschallenge.entities.session.requests;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.vote.entity.TypeVote;
import backvoteschallenge.entities.vote.entity.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VotingInSessionRequestTest {


    @Test
    @DisplayName("Deve converter Request em Model")
    void testConverterRequestToModel() {

        var modelEsperado = criaVoto();
        modelEsperado.setOrder(criaPauta());

        var request = new VotingInSessionRequest(1L, TypeVote.Nao);

        var returnModel = request.toModel(criaAssociado());
        returnModel.setId(1L);
        returnModel.setOrder(criaPauta());

        assertEquals(modelEsperado.getId(), returnModel.getId());
        assertEquals(modelEsperado.getTypeVote(), returnModel.getTypeVote());

        assertEquals(modelEsperado.getOrder().getId(), returnModel.getOrder().getId());

        assertEquals(modelEsperado.getAssociate().getId(), returnModel.getAssociate().getId());
        assertEquals(modelEsperado.getAssociate().getName(), returnModel.getAssociate().getName());
        assertEquals(modelEsperado.getAssociate().getDocument(), returnModel.getAssociate().getDocument());


        assertNotNull(request.getAssociateId());
        assertNotNull(request.getVote());
    }


    private Vote criaVoto() {
        Vote vote = new Vote();
        vote.setId(1L);
        vote.setTypeVote(TypeVote.Nao);
        vote.setOrder(criaPauta());
        vote.setAssociate(criaAssociado());

        return vote;
    }


    private Order criaPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");

        return order;
    }

    private Associate criaAssociado() {
        Associate associate = new Associate();
        associate.setId(1L);
        associate.setName("Robert C. Martin");
        associate.setDocument("52185458051");

        return associate;
    }
}