package dio.reactiveflashcards.domain.service;

import dio.reactiveflashcards.domain.document.UserDocument;
import dio.reactiveflashcards.domain.repository.UserRepository;
import dio.reactiveflashcards.domain.service.query.UserQueryService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;


@Slf4j
@Data
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryService queryService;


    public UserService(UserRepository userRepository, UserQueryService queryService) {
        this.userRepository = userRepository;
        this.queryService = queryService;
    }

    public Mono<UserDocument> save(@Validated UserDocument userDocument) {

        return userRepository.save(userDocument)
                .doFirst(() -> log.info("try to save a follow document {}", userDocument));
    }

    public Mono<UserDocument> update(final UserDocument document) {

        return queryService.findById(document.id())
                .flatMap(user -> {
                    UserDocument docUp = document.toBuilder()
                            .createdAt(document.createdAt())
                            .updateAt(document.updateAt())
                            .build();
                    return userRepository.save(docUp);
                }).doFirst(()
                        -> log.info("=== Tentativa de atualizar o usuario com as informacoes a seguir ", document));
    }


    public Mono<Void> delete(final String id) {

        return queryService.findById(id)
                .flatMap(userRepository::delete)
                .doFirst(() -> log.info("Tentando excluir o usuario com o identificador {}", id));
    }


}
