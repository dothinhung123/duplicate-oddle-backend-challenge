package com.oddle.app.weather.model.openweathermap.httpresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oddle.app.weather.model.openweathermap.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherHttpResponse {
    private Coord coord;
    @JsonProperty(value = "weather")
    private List<WeatherInfo> weather;
    private String base;
    private Main main;
    private long visibility;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    @JsonProperty("timezone")
    private long timeZone;
    private long id;
    private String name;
    private long cod;

}
