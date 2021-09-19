package backvoteschallenge.core.kafka.producers;

import backvoteschallenge.core.kafka.dto.ResultsKafkaDto;
import backvoteschallenge.entities.order.responses.ResultsResponse;
import backvoteschallenge.entities.vote.entity.TypeVote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ResultsProducer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${kafka.topic.results}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, ResultsKafkaDto> kafkaTemplate;

    public void send(Map<TypeVote, Integer> results) {
        ResultsKafkaDto kafkaRequest = new ResultsKafkaDto(results);

        kafkaTemplate.send(topic, kafkaRequest).addCallback(
                sucess -> log.info("Message send"),
                failure -> log.warn("Message don't send")
        );
    }

}
