package com.oddle.app.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oddle.app.weather.dto.WeatherDTO;
import com.oddle.app.weather.model.response.AcknowledgeResponse;
import com.oddle.app.weather.service.WeatherService;
import com.oddle.app.weather.utility.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController implements WeatherOperations {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @Override
    public ResponseEntity<WeatherDTO> getCurrentWeatherByCity(String city) throws JsonProcessingException {
        WeatherDTO weatherDTO = weatherService.getCurrentWeatherByCity(city);
        return ResponseEntity.status(HttpStatus.OK).body(weatherDTO);
    }
    @Override
    public ResponseEntity<WeatherDTO> saveWeather(WeatherDTO weatherDTO) throws JsonProcessingException {
        WeatherDTO savedWeather = weatherService.saveWeather(weatherDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWeather);
    }
    @Override
    public ResponseEntity<List<WeatherDTO>> getWeatherHistory(LocalDate date) throws JsonProcessingException {
        List<WeatherDTO> weatherDTOList = weatherService.getWeatherByDateCreated(date);
        return ResponseEntity.status(HttpStatus.OK).body(weatherDTOList);
    }

    @Override
    public ResponseEntity<AcknowledgeResponse> deleteWeatherHistory(Long historyId) {
        boolean isAcknowledge = weatherService.deleteWeatherById(historyId);
        AcknowledgeResponse acknowledgeResponse = new AcknowledgeResponse();

        if (isAcknowledge) {
            acknowledgeResponse.setAcknowledge(Boolean.TRUE);
            acknowledgeResponse.setMessage(MessageUtils.SUCCESS);

        }
        return ResponseEntity.status(HttpStatus.OK).body(acknowledgeResponse);
    }

    @Override
    public ResponseEntity<WeatherDTO> updateWeatherHistory(WeatherDTO weatherDTO) throws JsonProcessingException {
        WeatherDTO updatedWeather = weatherService.updateWeather(weatherDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedWeather);
    }


}