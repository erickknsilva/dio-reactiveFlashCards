package dio.reactiveflashcards.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dio.reactiveflashcards.api.controller.response.ErrorFieldResponse;
import dio.reactiveflashcards.api.controller.response.ProblemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dio.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Component
public class WebExchangeBindExceptionHandler extends AbstractHandlerException<WebExchangeBindException> {

    private final MessageSource messageSource;

    public WebExchangeBindExceptionHandler(ObjectMapper objMapper, MessageSource messageSource) {
        super(objMapper);
        this.messageSource = messageSource;
    }

    @Override
    Mono<Void> handleException(ServerWebExchange exchange, WebExchangeBindException ex) {

        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, BAD_REQUEST);
                    return GENERIC_BAD_REQUEST.getMessage();
                }).map(message -> buildError(BAD_REQUEST, message))

                .flatMap(response -> buildParamErrorMessage2(response, ex))
                .doFirst(() ->
                        log.error(" === WebExchangeBindException: ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private Mono<ProblemResponse> buildParamErrorMessage2(final ProblemResponse problemResponse,
                                                          final WebExchangeBindException ex) {

        return Flux.fromIterable(ex.getAllErrors())

                .map(objectError -> ErrorFieldResponse.builder()
                        .name(objectError instanceof FieldError fieldError ?
                                fieldError.getField() : objectError.getObjectName())

                        .message(messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))
                        .build())
                .collectList()
                .map(problem -> problemResponse.toBuilder()
                        .fields(problem)
                        .build());

    }
}
