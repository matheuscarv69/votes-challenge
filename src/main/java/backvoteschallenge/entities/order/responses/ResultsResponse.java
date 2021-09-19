package backvoteschallenge.entities.order.responses;

import backvoteschallenge.entities.vote.entity.TypeVote;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

public class ResultsResponse {

    @ApiModelProperty(value = "Resultados da Votação da Pauta", example = "{\"Nao\": 0, \"Sim\": 3}")
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
