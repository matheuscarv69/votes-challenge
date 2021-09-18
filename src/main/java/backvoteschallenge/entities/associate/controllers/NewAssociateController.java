package backvoteschallenge.entities.associate.controllers;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.associate.repositories.AssociateRepository;
import backvoteschallenge.entities.associate.requests.NewAssociateRequest;
import backvoteschallenge.entities.associate.service.NewAssociateService;
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
@RequestMapping("/associate")
public class NewAssociateController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NewAssociateService service;

    @PostMapping("/new-associate")
    @Transactional
    public ResponseEntity<?> createNewAssociate(
            @RequestBody @Valid NewAssociateRequest request,
            UriComponentsBuilder uriBuilder) {
        log.info("Receiving request for create new Associate, name: {}", request.getName());

        Associate associate = service.saveNewAssociate(request);

        URI uri = uriBuilder
                .path("/associate/{associateId}")
                .buildAndExpand(associate.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }


}
