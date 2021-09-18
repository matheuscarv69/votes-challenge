package backvoteschallenge.entities.session.controller;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.session.requests.VotingInSessionRequest;
import backvoteschallenge.entities.session.service.ReceivingVotingInOrderServiceImpl;
import backvoteschallenge.entities.vote.entity.TypeVote;
import backvoteschallenge.entities.vote.entity.Vote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiveVoteInOrderController.class)
class ReceiveVoteInOrderControllerIntegrationTest {

    private final String URL = "/session/voting/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceivingVotingInOrderServiceImpl service;

    @Test
    @DisplayName("Deve efetuar um voto na Pauta da Sessão")
    void testDeveEfetuarVotoEmPauta() throws Exception {
        // cenario
        var sessionId = 1L;
        Mockito.doReturn(criaVoto()).when(service).executeVoting(Mockito.any(Long.class), Mockito.any(VotingInSessionRequest.class));

        // acao
        mockMvc.perform(
                post(URL + sessionId)
                        .content("{\"orderId\": 1,\"associateId\":\"1\",\"vote\":\"Nao\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        // validacao
        Mockito.verify(service, Mockito.times(1)).executeVoting(Mockito.any(Long.class), Mockito.any(VotingInSessionRequest.class));
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