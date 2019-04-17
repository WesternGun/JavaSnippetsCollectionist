package io.westerngun.exception;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * The generic error response DTO, containing information
 * which can be exposed to end user.
 * </p>
 * <p><strong>Should not contain full stacktrace of original
 * exception. </strong></p>
 *
 */
@RequiredArgsConstructor
public class GenericErrorResponse {

    /**
     * The error cause message; the message the end user
     * will see. Choose in the {@link ErrorType}.
     */
    private final String errorType;

    private final Integer errorCode;

}
