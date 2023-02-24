package com.oddle.app.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddle.app.weather.dto.WeatherDTO;
import com.oddle.app.weather.model.response.AcknowledgeResponse;
import com.oddle.app.weather.service.WeatherService;
import com.oddle.app.weather.utility.MessageUtils;
import org.hibernate.annotations.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    WeatherService weatherService;

    @DisplayName("get current weather by city")
    @ParameterizedTest
    @CsvSource({"London"})
    void getCurrentWeatherByCity(String city) throws Exception {
        String url ="/weather";
        WeatherDTO weatherDTO = WeatherDTO.builder()
                .id(1L)
                .description("Rain")
                .icon("10d")
                .main("rain")
                .build();
        //given
        when(weatherService.getCurrentWeatherByCity(city)).thenReturn(weatherDTO);
        //when
        MvcResult mvcResult = mockMvc.perform(get(url)
                        .param("city",city)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        String resultString = mvcResult.getResponse().getContentAsString();
        WeatherDTO expectedWeatherDTO = objectMapper.readValue(resultString,WeatherDTO.class);
        assertEquals(weatherDTO, expectedWeatherDTO);

    }

    @Test
    void saveWeather() throws Exception {
        String url ="/weather";
        WeatherDTO weatherDTO = WeatherDTO.builder()
                .id(1L)
                .description("Rain")
                .icon("10d")
                .main("rain")
                .build();
        //given
        when(weatherService.saveWeather(weatherDTO)).thenReturn(weatherDTO);
        //when
        MvcResult mvcResult = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(weatherDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        //then
        String resultString = mvcResult.getResponse().getContentAsString();
        WeatherDTO expectedWeatherDTO = objectMapper.readValue(resultString, WeatherDTO.class);
        assertEquals(expectedWeatherDTO, weatherDTO);
    }

    @Test
    void getWeatherHistory() throws Exception {
        String url ="/weather/history";
        LocalDate localDate = LocalDate.now();
        WeatherDTO weatherDTO = WeatherDTO.builder()
                .id(1L)
                .description("Rain")
                .icon("10d")
                .main("rain")
                .build();
        List<WeatherDTO> weatherDTOList = Arrays.asList(weatherDTO);
        //given
        when(weatherService.getWeatherByDateCreated(localDate)).thenReturn(weatherDTOList);
        //when
        MvcResult mvcResult = mockMvc.perform(get(url)
                        .param("date",localDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        String resultString = mvcResult.getResponse().getContentAsString();
        List<WeatherDTO> weatherDTOS = objectMapper.readValue(resultString, new TypeReference<List<WeatherDTO>>() {});
        assertEquals(1, weatherDTOS.size());
    }

    @ParameterizedTest
    @CsvSource({"1"})
    void deleteWeatherHistory(Long id) throws Exception {
        String url ="/weather/{id}";

        //given
        when(weatherService.deleteWeatherById(id)).thenReturn(Boolean.TRUE);
        //when
        MvcResult mvcResult = mockMvc.perform(delete(url,id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        String resultString = mvcResult.getResponse().getContentAsString();
        AcknowledgeResponse acknowledgeResponse = objectMapper.readValue(resultString, AcknowledgeResponse.class);
        assertEquals(MessageUtils.SUCCESS, acknowledgeResponse.getMessage());
        assertEquals(Boolean.TRUE, acknowledgeResponse.isAcknowledge());
    }

    @Test
    void updateWeatherHistory() throws Exception {
        String url ="/weather";
        WeatherDTO weatherDTO = WeatherDTO.builder()
                .id(1L)
                .description("Rain")
                .icon("10d")
                .main("rain")
                .build();
        //given
        when(weatherService.updateWeather(weatherDTO)).thenReturn(weatherDTO);
        //when
        MvcResult mvcResult = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weatherDTO)))
                .andExpect(status().isOk())
                .andReturn();
        //then
        String resultString = mvcResult.getResponse().getContentAsString();
        WeatherDTO expectedWeatherDTO = objectMapper.readValue(resultString, WeatherDTO.class);
        assertEquals(expectedWeatherDTO, weatherDTO);
    }
}