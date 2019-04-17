package io.westerngun.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ApplicationGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Set<ValidationErrorInfo> errors = new LinkedHashSet<>();

        ValidationErrorInfo validationError = new ValidationErrorInfo();
        BindingResult results = ex.getBindingResult();
        for (FieldError e: results.getFieldErrors()) {
            validationError.setDefaultMessage(e.getDefaultMessage());
            validationError.setFieldValue(e.getField());
            validationError.setRejectedValue(e.getRejectedValue().toString());
            validationError.setStatusCode(status);
            errors.add(validationError);
        }

        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(errors, ex.getMessage());

        return ResponseEntity.status(status.value())  // should always be 400 but we just use the incoming status
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(validationErrorResponse);
    }

    @ExceptionHandler(value = {ApplicationGlobalException.class})
    protected ResponseEntity<GenericErrorResponse> handleGenericException(ApplicationGlobalException ex) {
        ErrorType errorType = ex.getErrorType();
        // leave full stacktrace here, but don't expose the real
        // cause to end user
        log.error("Exception handler processing, error type: {}",
                errorType.getText(), ex);

        GenericErrorResponse genericErrorResponse = new GenericErrorResponse(
                errorType.getText(), errorType.getCode()
        );
        switch(errorType) {
            case INVALID_INCOMING_ARG:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(genericErrorResponse);
            case GENERIC_DATABASE_ERROR:
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(genericErrorResponse);
            case UNKNOWN_ERROR:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(genericErrorResponse);

            // TODO: map all error types; to be evaluated if all are needed
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(genericErrorResponse);
        }
    }

}
