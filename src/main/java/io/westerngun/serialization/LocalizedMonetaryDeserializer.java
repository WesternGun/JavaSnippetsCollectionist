package io.westerngun.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Localized monetary string (like "23,00", with comma as decimal separator and "."
 * as grouping/thousand separator) deserializer to convert it to a BigDecimal.
 * {@link BigDecimal} is the ideal type for monetary values because it is lossless.
 * Double is not the correct type where we require accuracy.
 *
 */
@Slf4j
public class LocalizedMonetaryDeserializer extends StdDeserializer<BigDecimal> {

    public LocalizedMonetaryDeserializer() {
        this(null);
    }


    public LocalizedMonetaryDeserializer(Class<String> b) {
        super(b);
    }

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String value = node.asText();
        try {
            Number number = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES")).parse(value); // for deserialization
            return new BigDecimal(number.toString());
        } catch (ParseException e) {
            log.error("Cannot parse localized number string {}", value, e);
        }
        return null;
    }

}
