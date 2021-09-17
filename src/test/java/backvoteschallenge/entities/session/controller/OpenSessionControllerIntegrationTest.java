package backvoteschallenge.entities.session.controller;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
import backvoteschallenge.entities.session.entity.Session.Session;
import backvoteschallenge.entities.session.repositories.SessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OpenSessionController.class)
class OpenSessionControllerIntegrationTest {

    private final String URL = "/session/open-session";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private SessionRepository sessionRepository;

    @Test
    @DisplayName("Deve abrir uma Sessão e retornar 201 Created")
    void testAbrirSessao() throws Exception {
        // cenario
        Mockito.doReturn(criarOptionalPauta()).when(orderRepository).findById(Mockito.any(Long.class));
        Mockito.doReturn(criarSession()).when(sessionRepository).save(Mockito.any(Session.class));

        // acao / validacao
        mockMvc.perform(
                post(URL)
                        .content("{\"orderId\": " + 1L + ",\" duration\":\" 2\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        Mockito.verify(orderRepository, Mockito.timeout(2)).findById(Mockito.any());
        Mockito.verify(sessionRepository, Mockito.timeout(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Não deve abrir uma sessão e deve retornar 400 Bad Request quando request for invalido")
    void testRetornarBadRequestAoAbrirSessaoQuandoRequestInvalido() throws Exception {

        // acao / validacao
        mockMvc.perform(
                post(URL)
                        .content("{\n" +
                                "\t\"orderId\": -211,\n" +
                                "\t\"duration\": sad\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    private Optional<Order> criarOptionalPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");
        order.setWhishes(new ArrayList<>());
        return Optional.of(order);
    }

    private Session criarSession() {
        Session session = new Session();
        session.setId(1L);
        session.setFinishedAt(LocalDateTime.now());
        session.setOrder(criarOptionalPauta().get());

        return session;
    }

}