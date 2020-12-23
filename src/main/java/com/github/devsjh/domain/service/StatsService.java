package com.github.devsjh.domain.service;

import com.github.devsjh.application.exception.InvalidJsonFormatException;
import com.github.devsjh.application.exception.NotFoundDataException;
import com.github.devsjh.application.payload.DailyStatsDto;
import com.github.devsjh.application.payload.HourlyStatsDto;
import com.github.devsjh.domain.model.DailyStats;
import com.github.devsjh.domain.model.HourlyStats;
import com.github.devsjh.domain.repository.DailyStatsRepository;
import com.github.devsjh.domain.repository.HourlyStatsRepository;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class StatsService {

    private final Gson gson;
    private final DailyStatsRepository dailyStatsRepository;
    private final HourlyStatsRepository hourlyStatsRepository;

    @Transactional
    public void store(String json) {
        DailyStats source; // 입력 데이터
        DailyStats target; // 기존 데이터

        try {
            source = gson.fromJson(json, DailyStatsDto.class).toEntity();
        } catch (JsonParseException | NullPointerException e) {
            // JSON 파일을 Dto 객체로 변환할 수 없을 경우
            throw new InvalidJsonFormatException();
        }

        target = dailyStatsRepository.findByDate(source.getDate());
        if (target == null) {
            // 날짜가 없을 경우 -> 전체 데이터를 등록한다.
            for (HourlyStats hourlyStats : new ArrayList<>(source.getHourlyStatsList())) {
                // 등록할 시각 데이터의 개수만큼 반복하며 매핑한다.
                hourlyStats.setDailyStats(source);
            }
            dailyStatsRepository.save(source);
        } else {
            // 날짜가 있을 경우 -> 시각 데이터를 등록 또는 갱신한다.
            for (HourlyStats sourceStats : source.getHourlyStatsList()) {
                // 등록/갱신할 시각 데이터의 개수만큼 반복한다.
                HourlyStats targetStats = hourlyStatsRepository.findByDailyStatsAndHour(target, sourceStats.getHour());

                if (targetStats == null) {
                    // 날짜가 있고 시각이 없을 경우 -> 없는 시각을 등록한다.
                    sourceStats.setDailyStats(target);
                } else {
                    // 날짜가 있고 시각이 있을 경우 -> 있는 시각을 갱신한다.
                    targetStats.update(sourceStats);
                }
            }
        }
    }

    @Transactional
    public String read(String date) {
        DailyStats dailyStats = dailyStatsRepository.findByDate(date);

        // 날짜로 조회한 데이터가 없을 경우
        if (dailyStats == null) throw new NotFoundDataException();

        // 요청, 응답, 클릭 횟수 합계
        int totalRequest = 0, totalResponse = 0, totalClick = 0;
        for (HourlyStats hourlyStats : dailyStats.getHourlyStatsList()) {
            totalRequest += hourlyStats.getRequest();
            totalResponse += hourlyStats.getResponse();
            totalClick += hourlyStats.getClick();
        }

        // Entity -> Dto
        DailyStatsDto dto = DailyStatsDto.builder()
                .totalDate(dailyStats.getDate())
                .totalRequest(totalRequest)
                .totalResponse(totalResponse)
                .totalClick(totalClick)
                .build();

        // Dto -> String
        return gson.toJson(dto);
    }

    @Transactional
    public String read(String date, int hour) {
        DailyStats dailyStats = dailyStatsRepository.findByDate(date);
        HourlyStats hourlyStats = hourlyStatsRepository.findByDailyStatsAndHour(dailyStats, hour);

        // 날짜와 시각으로 조회한 데이터가 없을 경우
        if (dailyStats == null || hourlyStats == null) throw new NotFoundDataException();

        // Entity -> Dto
        HourlyStatsDto dto = HourlyStatsDto.builder()
                .hour(hourlyStats.getHour())
                .request(hourlyStats.getRequest())
                .response(hourlyStats.getResponse())
                .click(hourlyStats.getClick())
                .build();

        // Dto -> String
        return gson.toJson(dto);
    }
}