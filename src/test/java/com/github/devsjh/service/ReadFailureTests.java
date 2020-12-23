package com.github.devsjh.service;

import com.github.devsjh.application.exception.NotFoundDataException;
import com.github.devsjh.domain.repository.DailyStatsRepository;
import com.github.devsjh.domain.repository.HourlyStatsRepository;
import com.github.devsjh.domain.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ReadFailureTests {

    // repository -> service (inject)
    @Mock private DailyStatsRepository dailyStatsRepository;
    @Mock private HourlyStatsRepository hourlyStatsRepository;
    @InjectMocks private StatsService statsService;

    @Test
    @DisplayName(value = "날짜로 데이터 조회")
    public void readByDate() {
        // Given (2020-01-01로 조회된 데이터가 없을 경우를 가정한다.)
        given(dailyStatsRepository.findByDate("2020-01-01"))
                .willReturn(null);

        // When
        Throwable exception = assertThrows(NotFoundDataException.class, () -> statsService.read("2020-01-01"));

        // Then
        System.out.println(exception.getMessage()); // Not Found Data
        assertEquals("Not Found Data", exception.getMessage());
    }

    @Test
    @DisplayName(value = "날짜/시각으로 데이터 조회")
    public void readByDateAndHour() {
        // Given (2020-01-01(1시)로 조회된 데이터가 없을 경우를 가정한다.)
        given(dailyStatsRepository.findByDate("2020-01-01"))
                .willReturn(null);
        given(hourlyStatsRepository.findByDailyStatsAndHour(null, 1))
                .willReturn(null);

        // When
        Throwable exception = assertThrows(NotFoundDataException.class, () -> statsService.read("2020-01-01", 1));

        // Then
        System.out.println(exception.getMessage()); // Not Found Data
        assertEquals("Not Found Data", exception.getMessage());
    }
}