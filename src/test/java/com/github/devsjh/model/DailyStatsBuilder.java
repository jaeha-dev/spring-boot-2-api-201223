package com.github.devsjh.model;

import com.github.devsjh.domain.model.DailyStats;
import com.github.devsjh.domain.model.HourlyStats;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DailyStatsBuilder {

    // DailyStats 생성
    public static DailyStats buildDailyStats() {
        DailyStats dailyStats = DailyStats.builder()
                .id(1L)
                .date("2020-01-01")
                .hourlyStatsList(new ArrayList<>())
                .build();
        List<HourlyStats> hourlyStatsList = Arrays.asList(
                HourlyStats.builder()
                        .id(1).hour(1).request(10).response(10).click(10).build(),
                HourlyStats.builder()
                        .id(2).hour(2).request(20).response(20).click(20).build()
        );

        // DailyStats <-> hourlyStatsList 관계 매핑
        for (HourlyStats hourlyStats : hourlyStatsList) {
            hourlyStats.setDailyStats(dailyStats);
        }

        return dailyStats;
    }
}