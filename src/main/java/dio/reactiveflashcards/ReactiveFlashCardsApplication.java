package dio.reactiveflashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
public class ReactiveFlashCardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveFlashCardsApplication.class, args);
    }

}
