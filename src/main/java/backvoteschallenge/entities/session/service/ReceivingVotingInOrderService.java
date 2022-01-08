package backvoteschallenge.entities.session.service;

import backvoteschallenge.entities.session.requests.VotingInSessionRequest;
import backvoteschallenge.entities.vote.entity.Vote;

public interface ReceivingVotingInOrderService {

    Vote executeVoting(Long sessionId, Long orderId, VotingInSessionRequest request);

}
