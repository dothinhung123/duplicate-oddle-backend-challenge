package com.oddle.app.weather.exception;

import com.oddle.app.weather.model.exceptions.ErrorMessage;
import com.oddle.app.weather.model.response.AcknowledgeResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@ControllerAdvice
public class WeatherExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {

        String errorMessageDescription = ex.getLocalizedMessage();

        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .errorMessageDescription(errorMessageDescription)
                .offsetDateTime(OffsetDateTime.now())
                .build();

        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = {WeatherNotFoundException.class})
    public ResponseEntity<Object> handleWeatherNotFoundException(WeatherNotFoundException ex, WebRequest request) {

        String errorMessageDescription = ex.getLocalizedMessage();

        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        AcknowledgeResponse acknowledgeResponse = AcknowledgeResponse
                .builder()
                .isAcknowledge(Boolean.FALSE)
                .offsetDateTime(OffsetDateTime.now())
                .message(errorMessageDescription)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(acknowledgeResponse);

    }

}
