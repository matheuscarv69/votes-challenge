package backvoteschallenge.core.kafka.dto;

import backvoteschallenge.entities.order.responses.ResultsResponse;
import backvoteschallenge.entities.vote.entity.TypeVote;

import java.util.HashMap;
import java.util.Map;

public class ResultsKafkaDto {

    private Map<TypeVote, Integer> results = new HashMap<>();

    public ResultsKafkaDto(Map<TypeVote, Integer> results) {
        this.results = results;
    }

    public ResultsKafkaDto(ResultsResponse response) {
        this.results = response.getResults();
    }


    public ResultsKafkaDto() {
    }

    public Map<TypeVote, Integer> getResults() {
        return results;
    }

    public void setResults(Map<TypeVote, Integer> results) {
        this.results = results;
    }
}
