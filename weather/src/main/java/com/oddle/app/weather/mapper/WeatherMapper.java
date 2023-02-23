package com.oddle.app.weather.mapper;

import com.oddle.app.weather.dto.WeatherDTO;
import com.oddle.app.weather.entity.WeatherEntity;
import com.oddle.app.weather.model.openweathermap.httpresponse.WeatherHttpResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    WeatherDTO weatherEntityToWeatherDTO(WeatherEntity weatherEntity);

    WeatherEntity weatherDTOToWeatherEntity(WeatherDTO weatherDTO);

    List<WeatherEntity> mapToWeatherEntityList(List<WeatherDTO> weatherEntityList);

    List<WeatherDTO> mapToWeatherDTOList(List<WeatherEntity> weatherDTOList);


}
