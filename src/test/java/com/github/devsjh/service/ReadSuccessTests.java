package com.github.devsjh.service;

import com.github.devsjh.domain.model.DailyStats;
import com.github.devsjh.domain.model.HourlyStats;
import com.github.devsjh.domain.repository.DailyStatsRepository;
import com.github.devsjh.domain.repository.HourlyStatsRepository;
import com.github.devsjh.domain.service.StatsService;
import com.github.devsjh.model.DailyStatsBuilder;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ReadSuccessTests {

    // repository -> service (inject)
    @Spy private Gson gson;
    @Mock private DailyStatsRepository dailyStatsRepository;
    @Mock private HourlyStatsRepository hourlyStatsRepository;
    @InjectMocks private StatsService statsService;

    @Test
    @DisplayName(value = "날짜로 데이터 조회")
    public void readByDate() {
        // Given (2020-01-01로 조회된 데이터가 있을 경우를 가정한다.)
        DailyStats dailyStats = DailyStatsBuilder.buildDailyStats();

        given(dailyStatsRepository.findByDate("2020-01-01"))
                .willReturn(dailyStats);

        // When
        String result = statsService.read("2020-01-01");
        // {"total_date":"2020-01-01","total_request":30,"total_response":30,"total_click":30}
        System.out.println(result);

        // Then
        assertThat(JsonPath.parse(result).read("$['total_date']").toString()).isEqualTo("2020-01-01");
        assertThat(JsonPath.parse(result).read("$['total_request']").toString()).isEqualTo("30");
    }

    @Test
    @DisplayName(value = "날짜/시각으로 데이터 조회")
    public void readByDateAndHour() {
        // Given (2020-01-01(1시)로 조회된 데이터가 있을 경우를 가정한다.)
        DailyStats dailyStats = DailyStatsBuilder.buildDailyStats();
        HourlyStats hourlyStats = dailyStats.getHourlyStatsList().get(0); // id = 1, hour = 1

        given(dailyStatsRepository.findByDate("2020-01-01"))
                .willReturn(dailyStats);
        given(hourlyStatsRepository.findByDailyStatsAndHour(dailyStats, 1))
                .willReturn(hourlyStats);

        // When
        String result = statsService.read("2020-01-01", 1);
        // {"hour":1,"request":10,"response":10,"click":10}
        System.out.println(result);

        // Then
        assertThat(JsonPath.parse(result).read("$['hour']").toString()).isEqualTo("1");
        assertThat(JsonPath.parse(result).read("$['request']").toString()).isEqualTo("10");
    }
}