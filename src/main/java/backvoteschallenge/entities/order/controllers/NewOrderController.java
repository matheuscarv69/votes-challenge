package backvoteschallenge.entities.order.controllers;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
import backvoteschallenge.entities.order.requests.NewOrderRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

/**
 * Endpoint responsavel por salvar um nova pauta no sistema.
 *
 * Endpoint responsible for saving a new agenda in the system.
 * */
@Api(tags = "Pauta")
@RestController
@RequestMapping("/orders")
public class NewOrderController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository repository;

    @ApiOperation("Cadastra uma nova Pauta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pauta cadastrada com sucesso"),
            @ApiResponse(code = 400, message = "Requisição mal formatada"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    @PostMapping("/new-order")
    @Transactional
    public ResponseEntity<?> createNewOrder(
            @RequestBody @Valid NewOrderRequest request,
            UriComponentsBuilder uriBuilder) {
        log.info("Receiving request for create new order, theme: {}", request.getTheme());

        Order order = repository.save(request.toModel());

        log.info("Saved new Order");

        URI uri = uriBuilder
                .path("/orders/{order_id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
