package dio.reactiveflashcards.domain.repository;

import dio.reactiveflashcards.domain.document.DeckDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends ReactiveMongoRepository<DeckDocument, String> {
}
