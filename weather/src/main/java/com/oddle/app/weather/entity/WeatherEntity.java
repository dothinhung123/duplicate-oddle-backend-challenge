package com.oddle.app.weather.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(schema = "WEATHER")
@Data
public class WeatherEntity {

    @Id
    @GeneratedValue
    @Column(name="WEATHER_ID")
    private Long id;

    @Column(name = "MAIN")
    private String main;

    @Column(name="DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ICON")
    private String icon;

    @Column(name="DATE_CREATED", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(name="DATE_MODIFIED")
    @UpdateTimestamp
    private LocalDateTime dateModified;

}
