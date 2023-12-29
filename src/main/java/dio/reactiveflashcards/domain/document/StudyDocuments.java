package dio.reactiveflashcards.domain.document;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;
import java.util.Set;

@Document(collection = "studies")
public record StudyDocuments(
        @Id
        String id,
        String userId,

        StudyDeck studyDeck,
        Set<Question> question,

        @CreatedDate
        @Field("created_at")
        OffsetDateTime createdAt,
        @LastModifiedDate
        @Field("update_at")
        OffsetDateTime updateAt
) {
    @Builder(toBuilder = true)
    public StudyDocuments {
    }
}
