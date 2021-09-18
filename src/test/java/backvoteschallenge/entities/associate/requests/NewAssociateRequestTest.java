package backvoteschallenge.entities.associate.requests;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewAssociateRequestTest {

    @Test
    @DisplayName("Deve converter Request em Model")
    void testConverterRequestToModel() {
        var modelEsperado = new Associate("James Gosling", "93741716006");

        var request = new NewAssociateRequest("James Gosling", "93741716006");

        var returnToModel = request.toModel();

        assertEquals(modelEsperado.getId(), returnToModel.getId());
        assertEquals(modelEsperado.getName(), returnToModel.getName());
        assertEquals(modelEsperado.getDocument(), returnToModel.getDocument());
    }


}