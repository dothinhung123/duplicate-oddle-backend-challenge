package com.oddle.app.weather.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage implements Serializable {
    private OffsetDateTime offsetDateTime = OffsetDateTime.now();
    private String errorMessageDescription;
}
