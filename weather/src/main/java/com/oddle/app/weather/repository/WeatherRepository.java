package com.oddle.app.weather.repository;

import com.oddle.app.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity,Long> {
    List<WeatherEntity> findAllByDateCreatedBetween(LocalDateTime from , LocalDateTime to);

}
