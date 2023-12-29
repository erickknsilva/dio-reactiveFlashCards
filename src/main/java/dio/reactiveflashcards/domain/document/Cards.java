package dio.reactiveflashcards.domain.document;

import lombok.Builder;

public record Cards(String front, String back) {
    @Builder(toBuilder = true)
    public Cards {
    }
}
