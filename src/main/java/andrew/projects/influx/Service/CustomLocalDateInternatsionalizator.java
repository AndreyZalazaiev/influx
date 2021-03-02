package andrew.projects.influx.Service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class CustomLocalDateInternatsionalizator extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DateTimeFormatter pattern;
        String lang = LocaleContextHolder.getLocale().getLanguage();

        if (lang.equalsIgnoreCase("UK")) {
            pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.UK)
                    .withZone(ZoneId.of("Europe/Kiev"));

        } else {
            pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                    .withZone(ZoneId.of("Europe/London"));
        }
        jsonGenerator.writeObject((localDate.format(pattern)));
    }
}