package backvoteschallenge.entities.session.controller;

import backvoteschallenge.entities.session.requests.VotingInSessionRequest;
import backvoteschallenge.entities.session.service.ReceivingVotingInOrderService;
import backvoteschallenge.entities.vote.entity.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/session")
public class ReceiveVoteInOrderController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReceivingVotingInOrderService service;

    @PostMapping("/voting/{sessionId}")
    public ResponseEntity<?> receiveVotingInOrder(
            @PathVariable Long sessionId,
            @RequestBody @Valid VotingInSessionRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        log.info("Receiving request for vote in Session: {} at Order: {}", sessionId, request.getOrderId());

        Vote vote = service.executeVoting(sessionId, request);

        URI uri = uriBuilder
                .path("/vote/{vote_id}")
                .buildAndExpand(vote.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }


}
