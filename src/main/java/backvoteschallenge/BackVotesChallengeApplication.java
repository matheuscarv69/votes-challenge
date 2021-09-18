package backvoteschallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BackVotesChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackVotesChallengeApplication.class, args);
    }

}
