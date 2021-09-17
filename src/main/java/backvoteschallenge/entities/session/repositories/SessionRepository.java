package backvoteschallenge.entities.session.repositories;

import backvoteschallenge.entities.session.entity.Session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
