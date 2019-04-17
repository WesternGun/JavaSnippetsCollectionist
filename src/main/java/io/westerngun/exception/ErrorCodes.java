package io.westerngun.exception;

/**
 * Constants of internal error code, mapping actual error type with integers.
 *
 * Visibility is package-only to limit global usage; only exceptions need them.
 *
 */
class ErrorCodes {
    // TODO evaluate each of them and integrate into JFPS; some may not be needed here
    /* 1xxx: mal-formatted request or errors in request parameters */
    static final Integer GENERIC_REQUEST_ERROR = 1000;
    static final Integer INVALID_INCOMING_ARG = 1001;

    /* 2xxx: database errors */
    static final Integer GENERIC_DATABASE_ERROR = 2000;
    static final Integer DATABASE_CONNECTION_ERROR = 2001; // cannot connect to database
    static final Integer DATA_INTEGRITY_ERROR = 2002; // data constraint violation, etc

    /* 3xxx: 3rd party provider error */
    static final Integer GENERIC_PROVIDER_ERROR = 3000;
    static final Integer PROVIDER_CONNECTION_ERROR = 3001;


    /* 4xxx: internal error (cannot process data, code error, ...) */
    static final Integer GENERIC_APPLICATION_FAILURE = 4000;


    /* 9999: unknown error */
    static final Integer UNKNOWN_ERROR = 9999;
}
