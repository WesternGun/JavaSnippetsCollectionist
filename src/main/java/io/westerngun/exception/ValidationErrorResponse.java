package io.westerngun.exception;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final Set<ValidationErrorInfo> validationErrors;
    private final String exceptionMessage;
}
