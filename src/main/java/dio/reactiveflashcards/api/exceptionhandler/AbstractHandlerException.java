package dio.reactiveflashcards.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dio.reactiveflashcards.api.controller.response.ProblemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;


@RequiredArgsConstructor
public abstract class AbstractHandlerException<T extends Exception> {


    private final ObjectMapper objMapper;


    abstract Mono<Void> handleException(final ServerWebExchange exchange, final T ex);


    protected Mono<Void> writeResponse(final ServerWebExchange exchange, final ProblemResponse problemResponse) {
        return exchange.getResponse().
                writeWith(Mono.fromCallable(() ->
                        new DefaultDataBufferFactory()
                                .wrap(objMapper.writeValueAsBytes(problemResponse))));
    }

    protected ProblemResponse buildError(final HttpStatus status, final String errorDescription) {
        return ProblemResponse.builder()
                .status(status.value())
                .errorDescription(errorDescription)
                .timeStamp(OffsetDateTime.now())
                .build();
    }

    protected void prepareExchange(final ServerWebExchange exchange, final HttpStatus statusCode) {

        exchange.getResponse().setStatusCode(statusCode);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    }

}
