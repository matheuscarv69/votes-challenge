package backvoteschallenge.entities.session.scheduled;

import backvoteschallenge.core.kafka.producers.ResultsProducer;
import backvoteschallenge.entities.session.repositories.SessionRepository;
import backvoteschallenge.entities.vote.entity.TypeVote;
import backvoteschallenge.entities.vote.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Essa classe executa um método agendado de 10 em 10 segundos,
 * ela busca as sessoes que estao com com o horario atual maior que a hora
 * de fim da sessao e faz o levantamento dos votos dessa sessao e seta a mesma
 * como fechada, depois disso envia os resultados para um topico no Kafka
 */
@Component
public class CheckSessionClosedScheduled {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ResultsProducer resultsProducer;

    @Scheduled(fixedDelayString = "${delay.scheluded.check.session.closed}")
    @Transactional
    protected void scheduledCheckSessionClosed() {
        var sessionList = sessionRepository.findSessionsClosed();

        sessionList.forEach(session -> {

            var voteList = voteRepository.findAllByOrderId(session.getOrder().getId());

            var resultTypeNo = (int) voteList
                    .stream()
                    .filter(vote -> vote.getTypeVote().equals(TypeVote.Nao)).count();

            var resultTypeYes = (int) voteList
                    .stream()
                    .filter(vote -> vote.getTypeVote().equals(TypeVote.Sim)).count();


            session.setOpen(false);
            sessionRepository.save(session);


            Map<TypeVote, Integer> results = new HashMap();
            results.put(TypeVote.Nao, resultTypeNo);
            results.put(TypeVote.Sim, resultTypeYes);

            resultsProducer.send(results);
        });

    }


}
