package backvoteschallenge.entities.associate.repositories;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociateRepository extends JpaRepository<Associate, Long> {

    Optional<Associate> findByDocument(String document);

}
