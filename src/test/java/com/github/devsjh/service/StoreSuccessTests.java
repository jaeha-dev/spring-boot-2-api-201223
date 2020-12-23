package com.github.devsjh.service;

import com.github.devsjh.application.payload.DailyStatsDto;
import com.github.devsjh.domain.model.DailyStats;
import com.github.devsjh.domain.model.HourlyStats;
import com.github.devsjh.domain.repository.DailyStatsRepository;
import com.github.devsjh.domain.repository.HourlyStatsRepository;
import com.github.devsjh.domain.service.StatsService;
import com.github.devsjh.model.DailyStatsBuilder;
import com.github.devsjh.model.JsonBuilder;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StoreSuccessTests {

    // repository -> service (inject)
    @Spy private Gson gson;
    @Mock private DailyStatsRepository dailyStatsRepository;
    @Mock private HourlyStatsRepository hourlyStatsRepository;
    @InjectMocks private StatsService statsService;

    @Test
    @DisplayName(value = "데이터 등록")
    public void storeDateAndHour() {
        // Given (2020-01-01(1시) 데이터를 새로 등록할 경우를 가정한다.)
        final String JSON = JsonBuilder.buildNewData();
        DailyStats source = gson.fromJson(JSON, DailyStatsDto.class).toEntity();

        given(dailyStatsRepository.findByDate(source.getDate())).willReturn(null); // DailyStats.target -> null

        // When
        statsService.store(JSON);

        // Then
        assertThat(source.getDate()).isEqualTo("2020-01-01");
        verify(dailyStatsRepository, times(1)).save(any(DailyStats.class)); // repo.save() 메서드 호출 횟수는 1이어야 한다.
    }

    @Test
    @DisplayName(value = "데이터 추가")
    public void storeHour() {
        // Given (2020-01-01(1, 2시) 데이터가 있을 때 2020-01-01(3시) 데이터를 추가 등록할 경우를 가정한다.)
        final String JSON = JsonBuilder.buildPartialData(); // 2020-01-01 / 3시 데이터
        DailyStats source = gson.fromJson(JSON, DailyStatsDto.class).toEntity();
        DailyStats target = DailyStatsBuilder.buildDailyStats(); // 2020-01-01 / 1, 2시 데이터

        // 날짜로 데이터를 조회하면 기존 데이터(target)을 반환한다.
        given(dailyStatsRepository.findByDate(source.getDate()))
                .willReturn(target);
        // 기존 데이터와 새로 추가할 시각(3시)으로 데이터를 조회하면 null을 반환한다.
        given(hourlyStatsRepository
                .findByDailyStatsAndHour(target, source.getHourlyStatsList().get(0).getHour()))
                .willReturn(null);

        // When
        statsService.store(JSON);

        // Then
        System.out.println(target.getHourlyStatsList().get(2).getHour());
        assertThat(target.getHourlyStatsList().get(2).getHour()).isEqualTo(3); // 추가된 3시 데이터
    }

    @Test
    @DisplayName(value = "데이터 갱신")
    public void updateHour() {
        // Given (2020-01-01(1, 2시) 데이터가 있을 때 2020-01-01(2시) 데이터를 갱신할 경우를 가정한다.)
        final String JSON = JsonBuilder.buildUpdateData(); // 2020-01-01 / 2시
        DailyStats source = gson.fromJson(JSON, DailyStatsDto.class).toEntity();
        DailyStats target = DailyStatsBuilder.buildDailyStats(); // 2020-01-01 / 1, 2시
        HourlyStats targetHourlyStats = target.getHourlyStatsList().get(1); // 2시 데이터

        // 날짜로 데이터를 조회하면 기존 데이터(target)을 반환한다.
        given(dailyStatsRepository.findByDate(source.getDate()))
                .willReturn(target);
        // 기존 데이터와 갱신할 시각(2시)으로 데이터를 조회하면 갱신할 HourlyStats 객체(2시)를 반환한다.
        given(hourlyStatsRepository
                .findByDailyStatsAndHour(target, source.getHourlyStatsList().get(0).getHour()))
                .willReturn(targetHourlyStats);

        // When
        statsService.store(JSON);

        // Then
        System.out.println(targetHourlyStats.getRequest());
        assertThat(targetHourlyStats.getRequest()).isEqualTo(200); // 갱신된 2시 데이터
    }
}