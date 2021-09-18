package backvoteschallenge.entities.session.requests;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.session.entity.Session.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenSessionRequestTest {

    @Test
    @DisplayName("Deve converter Request em Model")
    void testConverterRequestToModel() {
        LocalDateTime finish = LocalDateTime.now();
        Integer minutesFinish = 12;

        var modelEsperado = new Session(criarPauta(), finish);

        var request = new OpenSessionRequest(1L, minutesFinish);

        var returnModel = request.toModel(criarPauta());

        assertEquals(modelEsperado.getId(), returnModel.getId());
        assertEquals(modelEsperado.getOrder().getId(), returnModel.getOrder().getId());
        assertEquals(modelEsperado.getOrder().getTheme(), returnModel.getOrder().getTheme());
        assertEquals(modelEsperado.getOrder().getEditor(), returnModel.getOrder().getEditor());

        assertEquals(minutesFinish, request.getDuration());
        request.setDuration(13);
        assertEquals(13, request.getDuration());

        assertNotNull(returnModel.getCreatedAt());
        assertNotNull(returnModel.getFinishedAt());
    }

    private Order criarPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");
        return order;
    }

}