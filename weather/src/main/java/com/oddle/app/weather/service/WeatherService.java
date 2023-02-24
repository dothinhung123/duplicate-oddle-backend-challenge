package com.oddle.app.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oddle.app.weather.dto.WeatherDTO;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {
    /**
     * Search for todday weather of a specific city
     *
     * @param city
     * @return
     */
    WeatherDTO getCurrentWeatherByCity(String city) throws JsonProcessingException;

    /**
     * Save weather data
     *
     * @param weatherDTO
     * @return
     * @throws JsonProcessingException
     */
    WeatherDTO saveWeather(WeatherDTO weatherDTO) throws JsonProcessingException;

    /**
     * Get historical weather data
     */
    List<WeatherDTO> getWeatherByDateCreated(LocalDate date) throws JsonProcessingException;

    /**
     * Update historical weather data
     *
     * @param weatherDTO
     * @return
     * @throws JsonProcessingException
     */
    WeatherDTO updateWeather(WeatherDTO weatherDTO) throws JsonProcessingException;

    /**
     * Delete weather by id
     *
     * @param weatherId
     * @return
     */
    boolean deleteWeatherById(Long weatherId);
}

