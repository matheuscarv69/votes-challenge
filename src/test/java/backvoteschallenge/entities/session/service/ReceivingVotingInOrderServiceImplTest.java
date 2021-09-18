package backvoteschallenge.entities.session.service;

import backvoteschallenge.config.exception.*;
import backvoteschallenge.core.clients.cpfCheck.CpfCheckClient;
import backvoteschallenge.core.clients.cpfCheck.response.CpfCheckResponse;
import backvoteschallenge.core.clients.cpfCheck.response.StatusPossibleVote;
import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.associate.repositories.AssociateRepository;
import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
import backvoteschallenge.entities.session.entity.Session.Session;
import backvoteschallenge.entities.session.repositories.SessionRepository;
import backvoteschallenge.entities.session.requests.VotingInSessionRequest;
import backvoteschallenge.entities.vote.entity.TypeVote;
import backvoteschallenge.entities.vote.entity.Vote;
import backvoteschallenge.entities.vote.repositories.VoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ReceivingVotingInOrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private CpfCheckClient cpfCheckClient;

    @InjectMocks
    private ReceivingVotingInOrderServiceImpl service;

    @Test
    @DisplayName("Deve salvar Voto ")
    void testDeveSalvarVoto() {
        // cenario
        var sessionId = 1L;
        var orderId = 1L;
        var associateId = 1L;
        var vote = criaVoto();

        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(criarOptionalSessao());
        Mockito.when(associateRepository.findById(associateId)).thenReturn(criaOptionalAssociado());

        Mockito.when(cpfCheckClient.checkCpf(criaAssociado().getDocument())).thenReturn(criaCpfCheckResponse(StatusPossibleVote.ABLE_TO_VOTE));
        Mockito.when(orderRepository.findById(orderId)).thenReturn(criarOptionalPauta());

        ArgumentCaptor<Vote> voteArgumentCaptor = ArgumentCaptor.forClass(Vote.class);

        Mockito.when(voteRepository.save(voteArgumentCaptor.capture())).thenReturn(criaVoto());

        // acao
        var voteSaved = service.executeVoting(sessionId, criarRequest());

        // validacao
        Mockito.verify(sessionRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        Mockito.verify(associateRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        Mockito.verify(cpfCheckClient, Mockito.times(1)).checkCpf(Mockito.any(String.class));

        Assertions.assertNull(voteArgumentCaptor.getValue().getId());
        Assertions.assertEquals(vote.getAssociate().getId(), voteArgumentCaptor.getValue().getAssociate().getId());
        Assertions.assertEquals(vote.getAssociate().getName(), voteArgumentCaptor.getValue().getAssociate().getName());
        Assertions.assertEquals(vote.getAssociate().getDocument(), voteArgumentCaptor.getValue().getAssociate().getDocument());
        Assertions.assertEquals(vote.getOrder().getId(), voteArgumentCaptor.getValue().getOrder().getId());
        Assertions.assertEquals(vote.getOrder().getTheme(), voteArgumentCaptor.getValue().getOrder().getTheme());
        Assertions.assertEquals(vote.getOrder().getEditor(), voteArgumentCaptor.getValue().getOrder().getEditor());
    }

    @Test
    @DisplayName("Deve lançar SessionNotFoundException")
    void testDeveLancarSessionNotFoundException() {
        // cenario
        var sessionId = 1L;

        // acao
        var exception = assertThrows(SessionNotFoundException.class,
                () -> service.executeVoting(sessionId, criarRequest()));

        // validacao
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(SessionNotFoundException.class, exception.getClass());
        Assertions.assertEquals("Não foi encontrado nenhuma sessão com o ID informado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar AssociateNotFoundException")
    void testDeveLancarAssociateNotFoundException() {
        // cenario
        var sessionId = 1L;

        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(criarOptionalSessao());

        // acao
        var exception = assertThrows(AssociateNotFoundException.class, () -> service.executeVoting(sessionId, criarRequest()));

        // validacao
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(AssociateNotFoundException.class, exception.getClass());
        Assertions.assertEquals("Não foi encontrado nenhum associado com o ID informado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar AssociateUnableToVoteException")
    void testDeveLancarAssociateUnableToVoteException() {
        // cenario
        var sessionId = 1L;
        var associateId = 1L;

        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(criarOptionalSessao());
        Mockito.when(associateRepository.findById(associateId)).thenReturn(criaOptionalAssociado());
        Mockito.when(cpfCheckClient.checkCpf(criaAssociado().getDocument())).thenReturn(criaCpfCheckResponse(StatusPossibleVote.UNABLE_TO_VOTE));

        // acao
        var exception = assertThrows(AssociateUnableToVoteException.class, () -> service.executeVoting(sessionId, criarRequest()));

        // validacao
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(AssociateUnableToVoteException.class, exception.getClass());
        Assertions.assertEquals("Associado não esta habilitado à votar.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar OrderNotFoundException")
    void testDeveLancarOrderNotFoundException() {
        // cenario
        var sessionId = 1L;
        var associateId = 1L;

        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(criarOptionalSessao());
        Mockito.when(associateRepository.findById(associateId)).thenReturn(criaOptionalAssociado());
        Mockito.when(cpfCheckClient.checkCpf(criaAssociado().getDocument())).thenReturn(criaCpfCheckResponse(StatusPossibleVote.ABLE_TO_VOTE));

        // acao
        var exception = assertThrows(OrderNotFoundException.class, () -> service.executeVoting(sessionId, criarRequest()));

        // validacao
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(OrderNotFoundException.class, exception.getClass());
        Assertions.assertEquals("Não foi encontrada nenhuma pauta com o ID informado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar AssociateAlreadyVotedException")
    void testDeveLancarAssociateAlreadyVotedException() {
        // cenario
        var sessionId = 1L;
        var orderId = 1L;
        var associateId = 1L;

        Mockito.when(sessionRepository.findById(sessionId)).thenReturn(criarOptionalSessao());
        Mockito.when(associateRepository.findById(associateId)).thenReturn(criaOptionalAssociado());
        Mockito.when(cpfCheckClient.checkCpf(criaAssociado().getDocument())).thenReturn(criaCpfCheckResponse(StatusPossibleVote.ABLE_TO_VOTE));
        Mockito.when(orderRepository.findById(orderId)).thenReturn(criarOptionalPautaVotada());

        // acao
        var exception = assertThrows(AssociateAlreadyVotedException.class, () -> service.executeVoting(sessionId, criarRequest()));

        // validacao
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(AssociateAlreadyVotedException.class, exception.getClass());
        Assertions.assertEquals("O Associado informado já votou nesta Pauta.", exception.getMessage());
    }

    private CpfCheckResponse criaCpfCheckResponse(StatusPossibleVote statusPossibleVote) {
        return new CpfCheckResponse(statusPossibleVote);
    }

    private Optional<Session> criarOptionalSessao() {
        Session session = new Session();
        session.setId(1L);
        session.setFinishedAt(LocalDateTime.now().plusMinutes(100));
        session.setOrder(criarOptionalPauta().get());

        return Optional.of(session);
    }

    private VotingInSessionRequest criarRequest() {
        return new VotingInSessionRequest(1L, 1L, TypeVote.Nao);
    }

    private Vote criaVoto() {
        Vote vote = new Vote();
        vote.setId(1L);
        vote.setTypeVote(TypeVote.Nao);
        vote.setOrder(criaPauta());
        vote.setAssociate(criaAssociado());

        return vote;
    }

    private Order criaPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");

        return order;
    }

    private Optional<Order> criarOptionalPauta() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");
        return Optional.of(order);
    }

    private Optional<Order> criarOptionalPautaVotada() {
        Order order = new Order();
        order.setId(1L);
        order.setTheme("Clean Code is great?");
        order.setEditor("Uncle Bob");
        order.addVote(criaVoto());
        return Optional.of(order);
    }

    private Associate criaAssociado() {
        Associate associate = new Associate();
        associate.setId(1L);
        associate.setName("Robert C. Martin");
        associate.setDocument("52185458051");

        return associate;
    }

    private Optional<Associate> criaOptionalAssociado() {
        Associate associate = new Associate();
        associate.setId(1L);
        associate.setName("Robert C. Martin");
        associate.setDocument("52185458051");

        return Optional.of(associate);
    }
}