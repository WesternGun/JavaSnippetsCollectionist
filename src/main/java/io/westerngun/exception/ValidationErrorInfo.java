package io.westerngun.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

/**
 * Error response DTO only for incoming JSON request validation, with specific
 * properties distint to {@link GenericErrorResponse}.
 *
 * Should only be used in {@link ApplicationGlobalExceptionHandler#handleMethodArgumentNotValid(
 * MethodArgumentNotValidException, HttpHeaders, HttpStatus, WebRequest)}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorInfo {
    private String defaultMessage;

    private String rejectedValue;

    private String fieldValue;

    private HttpStatus statusCode;
}
