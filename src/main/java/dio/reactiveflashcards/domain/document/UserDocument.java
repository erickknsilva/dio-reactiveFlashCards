package dio.reactiveflashcards.domain.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;

@Document(collection = "users")
public record UserDocument(
        @Id
        String id,

        @NotBlank
        @Size(min = 1, max = 255)
        @JsonProperty("name")
        String name,


        @NotBlank
        @Size(min = 1, max = 255)
        @Email
        String email,
        @CreatedDate
        @Field("created_at")
        OffsetDateTime createdAt,
        @LastModifiedDate
        @Field("update_at")
        OffsetDateTime updateAt

) {

    @Builder(toBuilder = true)
    public UserDocument {
    }

}
