package dio.reactiveflashcards.domain.repository;

import dio.reactiveflashcards.domain.document.DeckDocument;
import dio.reactiveflashcards.domain.document.StudyDocuments;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends ReactiveMongoRepository<StudyDocuments, String> {
}
