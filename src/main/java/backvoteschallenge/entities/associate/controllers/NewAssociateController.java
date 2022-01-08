package backvoteschallenge.entities.associate.controllers;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.associate.requests.NewAssociateRequest;
import backvoteschallenge.entities.associate.service.NewAssociateService;
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
 * Endpoint responsavel por salvar um novo associado ao sistema.
 *
 * Endpoint responsible for saving a new one associated with the system.
 * */
@Api(tags = "Associado")
@RestController
@RequestMapping("/associate")
public class NewAssociateController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NewAssociateService service;

    @ApiOperation("Cadastra um novo Associado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Associado cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição mal formatada"),
            @ApiResponse(code = 422, message = "CPF informado já existe no sistema"),
            @ApiResponse(code = 500, message = "Erro interno")
    })
    @PostMapping
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
