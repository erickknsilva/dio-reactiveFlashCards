package dio.reactiveflashcards.core.mongo;

import dio.reactiveflashcards.core.mongo.converter.DateToOffSetDateTimeConverter;
import dio.reactiveflashcards.core.mongo.converter.OffSetDateToDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MongoConfig {

    @Bean
    MongoCustomConversions mongoCustomConversions() {
        final List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new OffSetDateToDateTimeConverter());
        converters.add(new DateToOffSetDateTimeConverter());

        return new MongoCustomConversions(converters);
    }


}
