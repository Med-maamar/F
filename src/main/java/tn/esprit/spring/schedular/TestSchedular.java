package tn.esprit.spring.schedular;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TestSchedular {

    @Scheduled(fixedRate = 2000)
    public void affiche() {
        log.info("Bonjour");
    }

}
