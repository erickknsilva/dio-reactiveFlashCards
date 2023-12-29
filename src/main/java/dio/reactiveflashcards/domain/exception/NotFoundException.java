package dio.reactiveflashcards.domain.exception;


public class NotFoundException extends ReactiveFlashCardsException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(BaseErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}


