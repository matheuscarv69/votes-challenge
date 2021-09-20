package backvoteschallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class BackVotesChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackVotesChallengeApplication.class, args);
    }

}
