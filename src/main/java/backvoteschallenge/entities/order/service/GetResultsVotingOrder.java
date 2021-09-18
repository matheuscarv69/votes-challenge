package backvoteschallenge.entities.order.service;

import backvoteschallenge.entities.vote.entity.TypeVote;

import java.util.Map;

public interface GetResultsVotingOrder {

    Map<TypeVote, Integer> getResults(Long orderId);

}
