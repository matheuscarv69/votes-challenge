package backvoteschallenge.entities.associate.service;

import backvoteschallenge.config.exception.AssociateDocumentAlreadyExistsException;
import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.associate.repositories.AssociateRepository;
import backvoteschallenge.entities.associate.requests.NewAssociateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class NewAssociateServiceImpl implements NewAssociateService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AssociateRepository repository;

    @Override
    public Associate saveNewAssociate(NewAssociateRequest request) {

        verifyIfAssociateDocumentAlreadyExist(request.getDocument());

        Associate associate = request.toModel();

        repository.save(associate);
        log.info("Saved new Associate");

        return associate;
    }

    private void verifyIfAssociateDocumentAlreadyExist(String document) {
        if (repository.findByDocument(document).isPresent()) {
            throw new AssociateDocumentAlreadyExistsException();
        }
    }


}
