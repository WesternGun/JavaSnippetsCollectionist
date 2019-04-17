package io.westerngun.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * General exception of FenixOrchestator project. Visibility is public to enable
 * global usage. We distinguish the type of error with {@link ErrorType}.
 * </p>
 *
 * <p><strong>
 * All sub-classes should only contain {@link lombok.Getter} to allow recovery of
 * properties, but NO {@link lombok.Setter} to change the properties.
 * </strong></p>
 *
 */

@Getter
@RequiredArgsConstructor
public class ApplicationGlobalException extends RuntimeException {
    private final String message;

    private final Throwable cause;

    private final ErrorType errorType;

}
