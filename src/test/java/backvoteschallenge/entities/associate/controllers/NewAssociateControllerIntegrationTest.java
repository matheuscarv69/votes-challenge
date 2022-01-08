package backvoteschallenge.entities.associate.controllers;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.associate.requests.NewAssociateRequest;
import backvoteschallenge.entities.associate.service.NewAssociateServiceImpl;
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

@WebMvcTest(NewAssociateController.class)
class NewAssociateControllerIntegrationTest {

    private final String URL = "/associate";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewAssociateServiceImpl service;

    @Test
    @DisplayName("Deve criar um Associate e retornar 201 Created")
    void testCriarAssociado() throws Exception {
        // cenario
        Mockito.doReturn(criarAssociado()).when(service).saveNewAssociate(Mockito.any(NewAssociateRequest.class));

        // acao / validacao
        mockMvc.perform(
                post(URL)
                        .content("{\"name\":\"Matheus Carvalho\",\"document\":\"83777293008\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        Mockito.verify(service, Mockito.timeout(1)).saveNewAssociate(Mockito.any(NewAssociateRequest.class));
    }

    @Test
    @DisplayName("Deve tentar criar um Associate e retornar 400 Bad Request")
    void testDeveRetornarBadRequest() throws Exception {

        // acao / validacao
        mockMvc.perform(
                post(URL)
                        .content("{\"name\":\"\",\"document\":\"\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    private Associate criarAssociado() {
        Associate associate = new Associate("James Gosling", "93741716006");
        associate.setId(1L);

        return associate;
    }


}