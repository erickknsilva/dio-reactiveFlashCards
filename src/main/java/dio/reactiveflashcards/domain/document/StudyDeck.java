package dio.reactiveflashcards.domain.document;

import lombok.Builder;

import java.util.Set;

public record StudyDeck(
        String deckId,
        Set<StudyCard> cardsSet
) {
    @Builder(toBuilder = true)
    public StudyDeck {
    }
}
