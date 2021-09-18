package backvoteschallenge.core.clients.cpfCheck;

import backvoteschallenge.core.clients.cpfCheck.response.CpfCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Cpf-Check-Client", url = "${url.api.checking.cpf}")
public interface CpfCheckClient {

    @GetMapping("/{cpf}")
    CpfCheckResponse checkCpf(@PathVariable String cpf);


}
