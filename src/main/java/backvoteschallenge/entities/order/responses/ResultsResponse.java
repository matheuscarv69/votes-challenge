package backvoteschallenge.entities.order.responses;

import backvoteschallenge.entities.vote.entity.TypeVote;

import java.util.HashMap;
import java.util.Map;

public class ResultsResponse {

    private Map<TypeVote, Integer> results = new HashMap<>();

    public ResultsResponse(Map<TypeVote, Integer> results) {
        this.results = results;
    }

    public ResultsResponse() {
    }

    public Map<TypeVote, Integer> getResults() {
        return results;
    }

    public void setResults(Map<TypeVote, Integer> results) {
        this.results = results;
    }
}
