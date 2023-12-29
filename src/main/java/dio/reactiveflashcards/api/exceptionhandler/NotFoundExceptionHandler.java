package dio.reactiveflashcards.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dio.reactiveflashcards.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
public class NotFoundExceptionHandler extends AbstractHandlerException<NotFoundException> {

    public NotFoundExceptionHandler(ObjectMapper objMapper) {
        super(objMapper);
    }

    @Override
    Mono<Void> handleException(ServerWebExchange exchange, NotFoundException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, NOT_FOUND);
                    return ex.getMessage();
                }).map(message -> buildError(NOT_FOUND, message))
                .doFirst(() ->
                        log.error(" === NotFoundException: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

}
