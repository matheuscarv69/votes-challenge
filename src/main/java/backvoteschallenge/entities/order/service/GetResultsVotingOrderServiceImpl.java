package backvoteschallenge.entities.order.service;

import backvoteschallenge.config.exception.OrderNotFoundException;
import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
import backvoteschallenge.entities.vote.entity.TypeVote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Primary
@Component
public class GetResultsVotingOrderServiceImpl implements GetResultsVotingOrder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository repository;

    @Override
    public Map<TypeVote, Integer> getResults(Long orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        log.info("Getting whishes by Order");

        var resultTypeNO = order.getWhishes()
                .stream()
                .filter(vote -> vote.getTypeVote().equals(TypeVote.Nao))
                .collect(Collectors.toList());

        var resultTypeYes = order.getWhishes()
                .stream()
                .filter(vote -> vote.getTypeVote().equals(TypeVote.Sim))
                .collect(Collectors.toList());

        Map<TypeVote, Integer> results = new HashMap();

        results.put(TypeVote.Nao, resultTypeNO.size());
        results.put(TypeVote.Sim, resultTypeYes.size());

        return results;
    }
}
