package backvoteschallenge.entities.order.service;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
import backvoteschallenge.entities.vote.entity.TypeVote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GetResultsVotingOrderServiceImplTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private GetResultsVotingOrderServiceImpl service;

    @Test
    @DisplayName("Deve buscar resultados da votação de uma Pauta")
    void testBuscarResultadoDeVotacaoEmPauta() {
        // cenario
        var orderId = 1L;
        var responseEsperado = criaMapResultados();

        Mockito.when(repository.findById(orderId)).thenReturn(criarOptionalPauta());

        // acao
        var response = service.getResults(orderId);

        // validacao
        assertNotNull(response);
        Assertions.assertEquals(responseEsperado.get(TypeVote.Sim), response.get(TypeVote.Sim));
        Assertions.assertEquals(responseEsperado.get(TypeVote.Nao), response.get(TypeVote.Nao));
    }

    private Optional<Order> criarOptionalPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");
        return Optional.of(order);
    }

    private Map<TypeVote, Integer> criaMapResultados() {

        Map<TypeVote, Integer> results = new HashMap();
        results.put(TypeVote.Nao, 0);
        results.put(TypeVote.Sim, 0);

        return results;
    }


}