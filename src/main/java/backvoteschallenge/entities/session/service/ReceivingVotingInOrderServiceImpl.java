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
import backvoteschallenge.entities.vote.entity.Vote;
import backvoteschallenge.entities.vote.repositories.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Primary
@Component
public class ReceivingVotingInOrderServiceImpl implements ReceivingVotingInOrderService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CpfCheckClient cpfClient;

    @Override
    @Transactional
    public Vote executeVoting(Long sessionId, VotingInSessionRequest request) {

        checkIfSessionIsOpen(sessionId);

        Associate associate = associateRepository.findById(request.getAssociateId())
                .orElseThrow(AssociateNotFoundException::new);

        checkAssociateAbleToVote(associate);

        Order order = verifyIfAssociateAlreadyVoted(request, associate);

        Vote vote = request.toModel(associate);

        order.addVote(vote);

        voteRepository.save(vote);
        log.info("Saved Vote: {} in Order: {}", vote.getId(), order.getTheme());

        return vote;
    }

    private void checkIfSessionIsOpen(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(SessionNotFoundException::new);

        if (!session.isOpen()) {
            throw new SessionIsClosedException();
        }
    }

    private Order verifyIfAssociateAlreadyVoted(VotingInSessionRequest request, Associate associate) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(OrderNotFoundException::new);

        if (order.associateAlreadyVoted(associate)) {
            throw new AssociateAlreadyVotedException();
        }

        return order;
    }

    private void checkAssociateAbleToVote(Associate associate) {
        CpfCheckResponse cpfCheckResponse = cpfClient.checkCpf(associate.getDocument());
        log.info("Associate possible voting: {}", cpfCheckResponse.getStatus());

        if (cpfCheckResponse.getStatus().equals(StatusPossibleVote.UNABLE_TO_VOTE)) {
            throw new AssociateUnableToVoteException();
        }
    }


}
