package dio.reactiveflashcards.api.controller.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record ErrorFieldResponse(
        String name,
        String message
) {
}
