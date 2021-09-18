package backvoteschallenge.entities.vote.repositories;

import backvoteschallenge.entities.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
