package backvoteschallenge.entities.session.entity.Session;

import backvoteschallenge.entities.order.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test
    @DisplayName("Deve retornar verdadeiro para Sessao Aberta")
    void testSessaoAbertaTrue() {
        // cenario
        LocalDateTime finish = LocalDateTime.now().plusMinutes(12);

        var model = new Session(criarPauta(), finish);

        Assertions.assertTrue(model.isOpen());
    }

    @Test
    @DisplayName("Deve retornar Falso para Sessao quando a data de fim for anterior a data atual")
    void testSessaoAbertaFalse() {
        // cenario
        LocalDateTime finish = LocalDateTime.now().minusMinutes(12);

        var model = new Session(criarPauta(), finish);

        Assertions.assertFalse(model.isOpen());
    }


    private Order criarPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");
        order.setWhishes(new ArrayList<>());
        return order;
    }
}