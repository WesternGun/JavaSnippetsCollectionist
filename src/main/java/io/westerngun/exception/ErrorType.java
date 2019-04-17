package io.westerngun.exception;

import lombok.Getter;

/**
 * Enum of all possible error type in the application. Exception handling is based on
 * the type of error(status code, etc. )
 *
 * This class also contains the mapping between {@link ErrorCodes} and type of error.
 */
@Getter
public enum ErrorType {

    INVALID_INCOMING_ARG(ErrorCodes.INVALID_INCOMING_ARG, "Incoming argument(s) invalid"),
    GENERIC_DATABASE_ERROR(ErrorCodes.GENERIC_DATABASE_ERROR, "Database error"),
    GENERIC_APPLICATION_FAILURE(ErrorCodes.GENERIC_APPLICATION_FAILURE, "Application failure"),
    UNKNOWN_ERROR(ErrorCodes.UNKNOWN_ERROR, "Unknown error");

    private final Integer code;
    private final String text;

    ErrorType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Error type: " + text + ", code: " + code;
    }
}
