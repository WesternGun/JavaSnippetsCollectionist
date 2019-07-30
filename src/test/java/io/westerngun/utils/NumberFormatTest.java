package io.westerngun.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberFormatTest {
    @Test
    @DisplayName("how to get Spain locale")
    void testDecimalFormatWithComma() throws ParseException {
        for (Locale locale: NumberFormat.getAvailableLocales()) {
            // System.out.println(locale.toString()); // prep work, a long list
            if (locale.toString().equals("es_ES")) { // the value we found in the long list: "es_ES"
                System.out.println(locale.toString());
                System.out.println(locale.toLanguageTag()); // will get "es-ES"
            }
        }
        // the only way to get Spain locale
        Number number = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES")).parse("55.555,55");
        BigDecimal bigDecimal = new BigDecimal(number.toString());
        System.out.println(bigDecimal); // 55555.55, note the decimal point is changed to "."
    }
}
