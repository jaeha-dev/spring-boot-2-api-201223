package com.github.devsjh.application.payload;

import com.github.devsjh.domain.model.DailyStats;
import com.github.devsjh.domain.model.HourlyStats;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class DailyStatsDto {

    @SerializedName("total_date")
    private String totalDate;

    @SerializedName("total_request")
    private int totalRequest;

    @SerializedName("total_response")
    private int totalResponse;

    @SerializedName("total_click")
    private int totalClick;

    @Expose(deserialize = false)
    @SerializedName("total_hours")
    private List<HourlyStatsDto> totalHours;

    @Builder
    public DailyStatsDto(String totalDate, int totalRequest, int totalResponse, int totalClick, List<HourlyStatsDto> totalHours) {
        this.totalDate = totalDate;
        this.totalRequest = totalRequest;
        this.totalResponse = totalResponse;
        this.totalClick = totalClick;
        this.totalHours = totalHours;
    }

    // Dto -> Entity 변환
    public DailyStats toEntity() {
        List<HourlyStats> hourlyStats = totalHours.stream()
                .map(HourlyStatsDto::toEntity)
                .collect(Collectors.toList());

        return DailyStats.builder()
                .date(totalDate)
                .hourlyStatsList(hourlyStats)
                .build();
    }
}