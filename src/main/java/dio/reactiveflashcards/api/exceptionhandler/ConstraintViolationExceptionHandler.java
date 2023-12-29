package dio.reactiveflashcards.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dio.reactiveflashcards.api.controller.response.ErrorFieldResponse;
import dio.reactiveflashcards.api.controller.response.ProblemResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dio.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Slf4j
@Component
public class ConstraintViolationExceptionHandler extends AbstractHandlerException<ConstraintViolationException> {

    public ConstraintViolationExceptionHandler(ObjectMapper objMapper) {
        super(objMapper);
    }

    @Override
    Mono<Void> handleException(ServerWebExchange exchange, ConstraintViolationException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, BAD_REQUEST);
                    return GENERIC_BAD_REQUEST.getMessage();
                }).map(message -> buildError(BAD_REQUEST, message))

                .flatMap(response -> buildParamErrorMessage(response, ex))
                .doFirst(() ->
                        log.error(" === ConstraintViolationException: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }


    private Mono<ProblemResponse> buildParamErrorMessage(final ProblemResponse problemResponse,
                                                         final ConstraintViolationException ex) {

        return Flux.fromIterable(ex.getConstraintViolations())
                .map(constraintViolation -> ErrorFieldResponse.builder()
                        .name(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString())
                        .message(constraintViolation.getMessage()).build())
                .collectList()
                .map(problem -> problemResponse.toBuilder()
                        .fields(problem)
                        .build());
    }

}
