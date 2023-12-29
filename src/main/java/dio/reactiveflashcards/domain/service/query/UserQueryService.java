package dio.reactiveflashcards.domain.service.query;


import dio.reactiveflashcards.api.controller.request.UserRequest;
import dio.reactiveflashcards.domain.document.UserDocument;
import dio.reactiveflashcards.domain.exception.BaseErrorMessage;
import dio.reactiveflashcards.domain.exception.NotFoundException;
import dio.reactiveflashcards.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@AllArgsConstructor
@Slf4j
@Service
public class UserQueryService {

    private final UserRepository userRepository;

    public Mono<UserDocument> findById(final String id) {
        return userRepository.findById(id)
                .doFirst(() -> log.info("Buscando um usuario com identificador {}", id))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new NotFoundException(BaseErrorMessage.USER_NOT_FOUND.params(id).getMessage()))));
    }


}
