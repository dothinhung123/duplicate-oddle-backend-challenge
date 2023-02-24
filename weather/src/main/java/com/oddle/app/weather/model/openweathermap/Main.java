package com.oddle.app.weather.model.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
    private double temp;
    @JsonProperty(value = "feels_like")
    private double feelsLike;
    @JsonProperty(value = "temp_min")
    private double tempMin;
    @JsonProperty(value = "temp_max")
    private double tempMax;
    private long pressure;
    private long humidity;
    @JsonProperty(value = "sea_level")
    private long seaLevel;
    @JsonProperty(value = "grnd_level")
    private long grndLevel;
}
