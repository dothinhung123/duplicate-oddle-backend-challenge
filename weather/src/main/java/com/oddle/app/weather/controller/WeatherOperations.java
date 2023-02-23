package com.oddle.app.weather.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.oddle.app.weather.dto.WeatherDTO;
import com.oddle.app.weather.model.exceptions.ErrorMessage;
import com.oddle.app.weather.model.response.AcknowledgeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weather")
public interface WeatherOperations {
    @Operation(summary = "Search for today weather of a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found today weather of a specific city",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Today weather of a specific city is not exists",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AcknowledgeResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "There are internal errors when search today weather of a specific city",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            })

    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<WeatherDTO> getCurrentWeatherByCity(@RequestParam("city") @NotNull String city) throws JsonProcessingException;

    @Operation(summary = "Save weather data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "save weather successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherDTO.class))
                    }),
            @ApiResponse(responseCode = "500", description = "There are internal errors when search today weather of a specific city",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    })

    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<WeatherDTO> saveWeather(@RequestBody WeatherDTO weatherDTO) throws JsonProcessingException ;

    @Operation(summary = "Get historical weather data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found weather history by date",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = WeatherDTO.class)))
                    }),
            @ApiResponse(responseCode = "400", description = "Today weather of a specific city is not exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AcknowledgeResponse.class))
                    }),
            @ApiResponse(responseCode = "500", description = "There are internal errors when search today weather of a specific city",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    })

    })
    @GetMapping(path = "/history", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<List<WeatherDTO>> getWeatherHistory(@RequestParam("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull LocalDate date) throws JsonProcessingException;

    @Operation(summary = "Delete historical weather data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a specific weather by id successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AcknowledgeResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Weather id is not exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AcknowledgeResponse.class))
                    }),
            @ApiResponse(responseCode = "500", description = "There are internal errors when search today weather of a specific city",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    })

    })
    @DeleteMapping(path = "/{id}")
    ResponseEntity<AcknowledgeResponse> deleteWeatherHistory(@PathVariable("id") @NotNull Long historyId) ;

    @Operation(summary = "Update historical weather data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update existing weather successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Today weather of a specific city is not exists",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AcknowledgeResponse.class))
                    }),
            @ApiResponse(responseCode = "500", description = "There are internal errors when search today weather of a specific city",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    })

    })
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<WeatherDTO> updateWeatherHistory(@RequestBody WeatherDTO weatherDTO) throws JsonProcessingException ;

}