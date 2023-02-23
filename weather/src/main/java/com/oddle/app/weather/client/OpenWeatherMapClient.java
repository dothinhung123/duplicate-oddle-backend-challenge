package com.oddle.app.weather.client;


import com.oddle.app.weather.helper.RestTemplateHelper;
import com.oddle.app.weather.model.openweathermap.httpresponse.WeatherHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Component
@Slf4j
public class OpenWeatherMapClient {
    private static final String WEATHER_URL="https://api.openweathermap.org/data/2.5/weather?q={city}&APPID={key}&units=metric";
    @Value("${api.openweathermap.key}")
    private String apiKey;
    private final RestTemplateHelper restTemplateHelper;



    public OpenWeatherMapClient( RestTemplateHelper restTemplateHelper) {
        this.restTemplateHelper = restTemplateHelper;
    }

    public WeatherHttpResponse getWeatherByCity(String city){
        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);
        WeatherHttpResponse weatherHttpResponse = restTemplateHelper.getForEntity(WeatherHttpResponse.class,url.toString());
        return weatherHttpResponse;
    }


}
