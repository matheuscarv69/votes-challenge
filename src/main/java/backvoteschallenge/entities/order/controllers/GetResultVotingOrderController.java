package backvoteschallenge.entities.order.controllers;

import backvoteschallenge.entities.order.responses.ResultsResponse;
import backvoteschallenge.entities.order.service.GetResultsVotingOrder;
import backvoteschallenge.entities.vote.entity.TypeVote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint responsavel por buscar os resultado de uma votacao em uma pauta.
 *
 * Endpoint responsible for fetching the results of a vote on an agenda.
 * */
@Api(tags = "Pauta")
@RestController
@RequestMapping("/orders")
public class GetResultVotingOrderController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GetResultsVotingOrder service;

    @ApiOperation("Busca resultados de uma Votação em uma Pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Pauta não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    @GetMapping("/{orderId}/results")
    public ResponseEntity<ResultsResponse> getResults(@PathVariable Long orderId) {
        log.info("Receiving request for get results the order: {}", orderId);

        Map<TypeVote, Integer> results = service.getResults(orderId);
        ResultsResponse response = new ResultsResponse(results);

        return ResponseEntity.ok().body(response);
    }


}
