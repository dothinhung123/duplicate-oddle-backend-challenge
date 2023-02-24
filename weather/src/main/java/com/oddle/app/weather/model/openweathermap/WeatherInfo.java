package com.oddle.app.weather.model.openweathermap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {
    private Long id;
    private String main;
    private String description;
    private String icon;
}
