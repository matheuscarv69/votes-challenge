package backvoteschallenge.entities.associate.service;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.associate.requests.NewAssociateRequest;

public interface NewAssociateService {

    Associate saveNewAssociate(NewAssociateRequest request);

}
