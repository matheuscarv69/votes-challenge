package backvoteschallenge.entities.order.controllers;

import backvoteschallenge.entities.order.service.GetResultsVotingOrderServiceImpl;
import backvoteschallenge.entities.vote.entity.TypeVote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetResultVotingOrderController.class)
class GetResultVotingOrderControllerTest {

    private final String URL = "/orders";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetResultsVotingOrderServiceImpl service;

    @Test
    @DisplayName("Deve retornar o resultado de uma votação e retornar 200 Ok")
    void testBuscarResultadoVotacao() throws Exception {
        // cenario
        var orderId = 1L;
        Mockito.doReturn(criaMapResultados()).when(service).getResults(Mockito.any(Long.class));

        // acao / validacao
        mockMvc.perform(
                get(URL + "/1/results")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    private Map<TypeVote, Integer> criaMapResultados() {

        Map<TypeVote, Integer> results = new HashMap();
        results.put(TypeVote.Nao, 1);
        results.put(TypeVote.Sim, 2);

        return results;
    }

}