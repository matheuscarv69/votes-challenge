package backvoteschallenge.entities.session.controller;

import backvoteschallenge.config.exception.OrderNotFoundException;
import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.order.repositories.OrderRepository;
import backvoteschallenge.entities.session.entity.Session.Session;
import backvoteschallenge.entities.session.repositories.SessionRepository;
import backvoteschallenge.entities.session.requests.OpenSessionRequest;
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

@RestController
@RequestMapping("/session")
public class OpenSessionController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/open-session")
    @Transactional
    public ResponseEntity<?> openSession(@RequestBody @Valid OpenSessionRequest request, UriComponentsBuilder uriBuilder) {
        log.info("Receiving request for open new session, order id: {}", request.getOrderId());

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(OrderNotFoundException::new);

        Session session = request.toModel(order);

        sessionRepository.save(session);
        log.info("Opening Session: {}", session.getId());

        URI uri = uriBuilder
                .path("/session/{session_id}")
                .buildAndExpand(session.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }


}
