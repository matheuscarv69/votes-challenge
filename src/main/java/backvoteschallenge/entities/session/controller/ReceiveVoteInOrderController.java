package backvoteschallenge.entities.session.controller;

import backvoteschallenge.entities.session.requests.VotingInSessionRequest;
import backvoteschallenge.entities.session.service.ReceivingVotingInOrderService;
import backvoteschallenge.entities.vote.entity.Vote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Endpoint responsavel por receber os votos em uma pauta.
 *
 * Endpoint responsible for receiving votes on an agenda.
 * */
@Api(tags = "Sessão")
@RestController
@RequestMapping("/session")
public class ReceiveVoteInOrderController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReceivingVotingInOrderService service;

    @ApiOperation("Realiza Voto em uma Pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pauta cadastrada com sucesso"),
            @ApiResponse(code = 400, message = "Requisição mal formatada"),
            @ApiResponse(code = 400, message = "Associado já votou na sessão"),
            @ApiResponse(code = 404, message = "Sessão não encontrada"),
            @ApiResponse(code = 404, message = "Associado não encontrado"),
            @ApiResponse(code = 422, message = "Sessão não está aberta"),
            @ApiResponse(code = 422, message = "Associado não está apto à votar"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
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
