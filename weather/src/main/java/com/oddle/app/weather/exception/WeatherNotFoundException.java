package com.oddle.app.weather.exception;

/**
 *
 * @overview This class represent for exception when
 *           Weather id is not found in the system
 * @author rose
 */
public class WeatherNotFoundException extends RuntimeException{
    public WeatherNotFoundException(String message) {
        super(message);
    }
}
