package io.westerngun.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check if an IP address (IPv4 or IPv6) is valid.
 * TODO:
 * Maybe we should use Apache Commons <code>InetAddressValidator</code> to do this job, because
 * it has dedicated part of logic for IPv6. IPv4 is fine. For now Apache Commons is not dependency.
 */
@Constraint(validatedBy = {IpAddressValidator.class})
@Documented
@Target( { ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIpAddress {
    /**
     * @return the default message if the validation fails
     */
    String message() default "Invalid IP address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}