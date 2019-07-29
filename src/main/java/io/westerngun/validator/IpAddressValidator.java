package io.westerngun.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<ValidIpAddress, String> {
    /* the only regex that discards segments starting with 0 */
    private static final Pattern PATTERN_IPV4 = Pattern.compile("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    private static final Pattern PATTERN_IPV6 = Pattern.compile("([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}");

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            return PATTERN_IPV4.matcher(value).matches() || PATTERN_IPV6.matcher(value).matches();
        } else {
            return true;
        }

    }
}
