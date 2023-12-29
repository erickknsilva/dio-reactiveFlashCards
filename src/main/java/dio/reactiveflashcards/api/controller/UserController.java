package dio.reactiveflashcards.api.controller;

import dio.reactiveflashcards.api.controller.request.UserRequest;
import dio.reactiveflashcards.api.controller.response.UserResponse;
import dio.reactiveflashcards.core.validation.MongoId;
import dio.reactiveflashcards.domain.service.UserService;
import dio.reactiveflashcards.domain.service.query.UserQueryService;
import dio.reactiveflashcards.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

    private final UserMapper userMapper;

    private final UserService userService;
    private final UserQueryService queryService;

    public UserController(UserMapper userMapper, UserService userService, UserQueryService queryService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.queryService = queryService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> save(@Valid @RequestBody UserRequest request) {
        /*Pega os dados que vai vim em Json, converte eles para o tipo unicode do java, e faz a inserção no banco de dados,
         * e depois mapeia para um UserReponse, para ser a respota*/
        return userService.save(userMapper.toDocument(request))
                .doFirst(() -> log.info("salvando um usuário com os dados a seguir {}", request))
                .map(userMapper::toResponse);

    }




}
