package backvoteschallenge.entities.session.repositories;

import backvoteschallenge.entities.session.entity.Session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("select ses from session ses where current_timestamp > ses.finishedAt and ses.open = true")
    List<Session> findSessionsClosed();

}
