package com.oddle.app.weather.model.response;

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
public class AcknowledgeResponse implements Serializable {
    private boolean isAcknowledge;
    private OffsetDateTime offsetDateTime = OffsetDateTime.now();
    private String message;
}
