package dio.reactiveflashcards.core.mongo.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.util.Date;

@Configuration
public class DateToOffSetDateTimeConverter implements Converter<OffsetDateTime, Date> {


    @Override
    public Date convert(OffsetDateTime source) {
        return Date.from(source.toInstant());
    }

}
