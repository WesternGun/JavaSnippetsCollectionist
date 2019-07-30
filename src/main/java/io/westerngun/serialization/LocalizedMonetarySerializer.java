package io.westerngun.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * BigDecimal serializer to convert it to a localized monetary string (like "23,00", with comma
 * as decimal separator and "." as grouping/thousand separator).
 * {@link BigDecimal} is the ideal type for monetary values because it is lossless.
 * Double is not the correct type where we require accuracy.
 */
@Slf4j
public class LocalizedMonetarySerializer extends StdSerializer<BigDecimal> {

    public LocalizedMonetarySerializer() {
        this(null);
    }

    public LocalizedMonetarySerializer(Class<BigDecimal> b) {
        super(b);
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            try {
                String result = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES")).format(value.toString()); // for deserialization
                gen.writeString(result);
            } catch (IllegalArgumentException e) {
                log.error("Cannot format value {} to localized number string", value, e);
            }
        }
        gen.writeNull();
    }
}
