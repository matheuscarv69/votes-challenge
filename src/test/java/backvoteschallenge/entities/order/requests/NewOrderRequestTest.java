package backvoteschallenge.entities.order.requests;

import backvoteschallenge.entities.order.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NewOrderRequestTest {

    @Test
    @DisplayName("Deve converter Request em Model")
    void testConverterRequestToModel() {
        var modelEsperado = new Order("O Codificador Limpo", "Robert C. Martin");

        var request = new NewOrderRequest("O Codificador Limpo", "Robert C. Martin");

        var returnToModel = request.toModel();

        assertEquals(modelEsperado.getId(), returnToModel.getId());
        assertEquals(modelEsperado.getTheme(), returnToModel.getTheme());
        assertEquals(modelEsperado.getEditor(), returnToModel.getEditor());
        assertEquals(modelEsperado.getWhishes(), returnToModel.getWhishes());

        assertNotNull(modelEsperado.getCreatedAt());
        assertNotNull(returnToModel.getCreatedAt());

        assertNotNull(request.getTheme());
        assertNotNull(request.getEditor());
    }

}