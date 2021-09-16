package backvoteschallenge.entities.order.controllers;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
import backvoteschallenge.entities.order.requests.NewOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class NewOrderController {

    @Autowired
    private OrderRepository repository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("new-order")
    public ResponseEntity<?> createNewOrder(
            @RequestBody @Valid NewOrderRequest request,
            UriComponentsBuilder uriBuilder) {
        log.info("Receiving request for create new order, theme: {}", request.getTheme());

        Order order = repository.save(request.toModel());

        log.info("Saving new Order");

        URI uri = uriBuilder
                .path("/orders/{order_id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
