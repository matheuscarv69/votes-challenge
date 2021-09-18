package backvoteschallenge.entities.order.controllers;

import backvoteschallenge.entities.order.responses.ResultsResponse;
import backvoteschallenge.entities.order.service.GetResultsVotingOrder;
import backvoteschallenge.entities.vote.entity.TypeVote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class GetResultVotingOrderController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GetResultsVotingOrder service;

    @GetMapping("/{orderId}/results")
    public ResponseEntity<ResultsResponse> getResults(@PathVariable Long orderId) {
        log.info("Receiving request for get results the order: {}", orderId);

        Map<TypeVote, Integer> results = service.getResults(orderId);
        ResultsResponse response = new ResultsResponse(results);

        return ResponseEntity.ok().body(response);
    }


}
