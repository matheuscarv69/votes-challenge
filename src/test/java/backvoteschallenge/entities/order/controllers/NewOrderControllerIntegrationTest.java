package backvoteschallenge.entities.order.controllers;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
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

@WebMvcTest(NewOrderController.class)
class NewOrderControllerIntegrationTest {

    private final String URL = "/orders/new-order";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository repository;

    @Test
    @DisplayName("Deve criar uma Pauta e retornar 201 Created")
    void testCriarPauta() throws Exception {
        // cenario
        Mockito.doReturn(criarPauta()).when(repository).save(Mockito.any());

        // acao
        mockMvc.perform(
                post(URL)
                        .content("{\"theme\": \"Microservices is better that monolit?\",\"editor\": \"Uncle Bob\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        Mockito.verify(repository, Mockito.timeout(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Não deve criar uma Pauta e deve retornar 400 Bad Request")
    void testRetornarBadRequestAoCriarPauta() throws Exception {
        // cenario

        // acao e validacao
        mockMvc.perform(
                post(URL)
                        .content("{\"theme\": \"\",\"editor\": \"\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }


    private Order criarPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");
        return order;
    }


}