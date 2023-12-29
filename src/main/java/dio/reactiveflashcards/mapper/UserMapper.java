package dio.reactiveflashcards.mapper;

import dio.reactiveflashcards.api.controller.request.UserRequest;
import dio.reactiveflashcards.api.controller.response.UserResponse;
import dio.reactiveflashcards.domain.document.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    UserDocument toDocument(final UserRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    UserDocument toDocument(final UserRequest request, final String id);

    UserResponse toResponse(final UserDocument document);
//#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 --jar build/lib/reactive-flashcards-1.0.0.jar

}
