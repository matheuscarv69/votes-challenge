package backvoteschallenge.entities.associate.service;

import backvoteschallenge.config.exception.AssociateAlreadyVotedException;
import backvoteschallenge.config.exception.AssociateDocumentAlreadyExistsException;
import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.associate.repositories.AssociateRepository;
import backvoteschallenge.entities.associate.requests.NewAssociateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewAssociateServiceImplTest {

    @Mock
    private AssociateRepository repository;

    @InjectMocks
    private NewAssociateServiceImpl service;

    @Test
    @DisplayName("Deve salvar um Associado")
    void testDeveSalvarAssociado() {
        // cenario
        var associadoEsperado = criarAssociado();

        Mockito.when(repository.findByDocument("46436625062")).thenReturn(Optional.empty());

        ArgumentCaptor<Associate> associateArgumentCaptor = ArgumentCaptor.forClass(Associate.class);
        Mockito.when(repository.save(associateArgumentCaptor.capture())).thenReturn(associadoEsperado);

        // acao
        var response = service.saveNewAssociate(criarNewAssociateRequest());

        // validacao
        assertNull(associateArgumentCaptor.getValue().getId());
        assertNotNull(response);
        assertEquals(associadoEsperado.getName(), associateArgumentCaptor.getValue().getName());
        assertEquals(associadoEsperado.getDocument(), associateArgumentCaptor.getValue().getDocument());
    }

    @Test
    @DisplayName("Deve lançar AssociateDocumentAlreadyExistsException")
    void testDeveLancarAssociateDocumentAlreadyExistsException() {
        // cenario
        var associadoEsperado = criarAssociado();
        Mockito.when(repository.findByDocument("46436625062")).thenReturn(Optional.of(associadoEsperado));

        // acao
        var exception = assertThrows(AssociateDocumentAlreadyExistsException.class,
                () -> service.saveNewAssociate(criarNewAssociateRequest())
        );

        // validacao
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(AssociateDocumentAlreadyExistsException.class, exception.getClass());
        Assertions.assertEquals("Foi encontrado um Associado com o CPF informado.", exception.getMessage());
    }

    private NewAssociateRequest criarNewAssociateRequest() {
        return new NewAssociateRequest("James Gosling", "46436625062");
    }

    private Associate criarAssociado() {
        Associate associate = new Associate("James Gosling", "46436625062");
        associate.setId(1L);

        return associate;
    }
}