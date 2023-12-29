package dio.reactiveflashcards.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dio.reactiveflashcards.domain.exception.ReactiveFlashCardsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static dio.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class ReactiveFlashCardsExceptionHandler extends AbstractHandlerException<ReactiveFlashCardsException> {
    public ReactiveFlashCardsExceptionHandler(ObjectMapper objMapper) {
        super(objMapper);
    }

    @Override
    Mono<Void> handleException(ServerWebExchange exchange, ReactiveFlashCardsException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("=== ReactiveFlashCardsException: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
