package com.oddle.app.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddle.app.weather.client.OpenWeatherMapClient;
import com.oddle.app.weather.dto.WeatherDTO;
import com.oddle.app.weather.entity.WeatherEntity;
import com.oddle.app.weather.exception.WeatherNotFoundException;
import com.oddle.app.weather.mapper.WeatherMapper;
import com.oddle.app.weather.model.openweathermap.httpresponse.WeatherHttpResponse;
import com.oddle.app.weather.repository.WeatherRepository;
import com.oddle.app.weather.utility.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService{

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;
    private final ObjectMapper objectMapper;

    private final OpenWeatherMapClient openWeatherMapClient;

    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherMapper weatherMapper, ObjectMapper objectMapper, OpenWeatherMapClient openWeatherMapClient) {
        this.weatherRepository = weatherRepository;
        this.weatherMapper = weatherMapper;
        this.objectMapper = objectMapper;
        this.openWeatherMapClient = openWeatherMapClient;
    }

    @Override
    public WeatherDTO getCurrentWeatherByCity(final String city) throws JsonProcessingException {
        log.info("START , getCurrentWeatherByCity with city ={}", city);
        WeatherHttpResponse weatherHttpResponse = openWeatherMapClient.getWeatherByCity(city);
        if (weatherHttpResponse == null || weatherHttpResponse.getWeather() == null || weatherHttpResponse.getWeather().isEmpty()) {
            throw new WeatherNotFoundException(MessageUtils.WEATHER_NOT_FOUND);
        }
        WeatherDTO weatherDTO = objectMapper.readValue(objectMapper.writeValueAsString(weatherHttpResponse.getWeather().stream().findFirst()), WeatherDTO.class);
        log.info("END , getCurrentWeatherByCity with city ={}", city);
        return weatherDTO;
    }

    @Override
    public WeatherDTO saveWeather(final WeatherDTO weatherDTO) throws JsonProcessingException {
        log.info("START , saveWeather with {}",objectMapper.writeValueAsString(weatherDTO));

        WeatherEntity weather = weatherMapper.weatherDTOToWeatherEntity(weatherDTO);
        WeatherEntity savedWeather = weatherRepository.save(weather);

        log.info("END , saveWeather with {}",objectMapper.writeValueAsString(weatherDTO));
        return weatherMapper.weatherEntityToWeatherDTO(savedWeather);
    }

    @Override
    public List<WeatherDTO> getWeatherByDateCreated(LocalDate date) throws JsonProcessingException {
        log.info("START , getWeatherByDateCreated with date = {}",date);
        List<WeatherEntity> weatherEntityList = weatherRepository
                .findAllByDateCreatedBetween(date.atStartOfDay()
                , date.plusDays(1).atStartOfDay());
        List<WeatherDTO> weatherDTOList = weatherMapper
                .mapToWeatherDTOList(weatherEntityList);
        log.info("{}",objectMapper.writeValueAsString(weatherDTOList));
        log.info("END , getWeatherByDateCreated with date = {}",date);
        return weatherDTOList;
    }

    @Override
    public WeatherDTO updateWeather(final WeatherDTO weatherDTO) throws JsonProcessingException {
        log.info("START, updateWeather with {}", objectMapper.writeValueAsString(weatherDTO));
        //check weatherId exist or not
        if (Objects.isNull(weatherDTO) || weatherDTO.getId() == null || weatherRepository.findById(weatherDTO.getId()).isEmpty()) {
            //throw exception
            log.error("NOT FOUND weather id = {}", weatherDTO.getId());
            throw new WeatherNotFoundException(MessageUtils.WEATHER_NOT_FOUND);

        }
        WeatherEntity weather = weatherMapper.weatherDTOToWeatherEntity(weatherDTO);
        WeatherEntity updatedWeather = weatherRepository.save(weather);

        log.info("END, updateWeather with {}", objectMapper.writeValueAsString(weatherDTO));
        return weatherMapper.weatherEntityToWeatherDTO(updatedWeather);

    }

    @Override
    public boolean deleteWeatherById(final Long weatherId) {
        log.info("START, deleteWeatherById with weatherId ={}", weatherId);
        //check weatherId exist or not
        if(weatherId==null || weatherRepository.findById(weatherId).isEmpty()){
            //throw exception
            log.error("NOT FOUND weather id ={}",weatherId);
            throw new WeatherNotFoundException(MessageUtils.WEATHER_NOT_FOUND);
        }
        weatherRepository.deleteById(weatherId);
        log.info("END, deleteWeatherById with weatherId ={}", weatherId);

        return true;

    }
}
