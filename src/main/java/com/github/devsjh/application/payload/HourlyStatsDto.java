package com.github.devsjh.application.payload;

import com.github.devsjh.domain.model.HourlyStats;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HourlyStatsDto {

    @SerializedName("hour")
    private int hour;

    @SerializedName("request")
    private int request;

    @SerializedName("response")
    private int response;

    @SerializedName("click")
    private int click;

    @Builder
    public HourlyStatsDto(int hour, int request, int response, int click) {
        this.hour = hour;
        this.request = request;
        this.response = response;
        this.click = click;
    }

    // Dto -> Entity 변환
    public HourlyStats toEntity() {
        return HourlyStats.builder()
                .hour(hour).request(request).response(response).click(click)
                .build();
    }
}