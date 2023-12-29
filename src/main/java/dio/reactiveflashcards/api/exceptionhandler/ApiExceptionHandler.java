package dio.reactiveflashcards.api.exceptionhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dio.reactiveflashcards.domain.exception.NotFoundException;
import dio.reactiveflashcards.domain.exception.ReactiveFlashCardsException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@Slf4j
@AllArgsConstructor
public class ApiExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objMapper;
    private final MessageSource messageSource;

    private final MethodNotAllowedHandler methodNotAllowedHandler;
    private final NotFoundExceptionHandler notFoundExceptionHandler;
    private final ConstraintViolationExceptionHandler constraintViolationExceptionHandler;
    private final WebExchangeBindExceptionHandler webExchangeBindExceptionHandler;
    private final ResponseStatusHandler responseStatusExceptionHandler;
    private final ReactiveFlashCardsExceptionHandler reactiveFlashCardsExceptionHandler;
    private final ExceptionHandler exceptionHandler;
    private final JsonProcessingExceptionHandler jsonProcessingExceptionHandler;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        return Mono.error(ex)
                .onErrorResume(MethodNotAllowedException.class,
                        e -> methodNotAllowedHandler.handleException(exchange, e)
                )
                .onErrorResume(NotFoundException.class,
                        e -> notFoundExceptionHandler.handleException(exchange, e)
                )
                .onErrorResume(ConstraintViolationException.class,
                        e -> constraintViolationExceptionHandler.handleException(exchange, e)
                )
                .onErrorResume(WebExchangeBindException.class,
                        e -> webExchangeBindExceptionHandler.handleException(exchange, e)
                )
                .onErrorResume(ResponseStatusException.class,
                        e -> responseStatusExceptionHandler.handleException(exchange, e)
                )
                .onErrorResume(ReactiveFlashCardsException.class,
                        e -> reactiveFlashCardsExceptionHandler.handleException(exchange, e)
                )
                .onErrorResume(Exception.class,
                        e -> exceptionHandler.handleException(exchange, e)
                )
                .onErrorResume(JsonProcessingException.class,
                        e -> jsonProcessingExceptionHandler.handleException(exchange, e)
                )
                .then();

    }


}
