package com.github.devsjh.domain.model;

import lombok.*;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "HOURLY_STATS")
public class HourlyStats {

    @Id
    @Column(name = "HOURLY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int hour; // 집계 시각 정보 (ex. 22)
    private int request; // 광고 요청 수
    private int response; // 광고 응답 수
    private int click; // 광고 클릭 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DAILY_ID")
    private DailyStats dailyStats;

    @Builder
    public HourlyStats(int id, DailyStats dailyStats, int hour, int request, int response, int click) {
        this.id = id;
        this.dailyStats = dailyStats;
        this.hour = hour;
        this.request = request;
        this.response = response;
        this.click = click;
    }

    public void setDailyStats(DailyStats dailyStats) {
        if (this.dailyStats != null) {
            this.dailyStats.getHourlyStatsList().remove(this);
        }

        this.dailyStats = dailyStats;
        dailyStats.getHourlyStatsList().add(this);
    }

    public void update(HourlyStats hourlyStats) {
        this.hour = hourlyStats.getHour();
        this.request = hourlyStats.getRequest();
        this.response = hourlyStats.getResponse();
        this.click = hourlyStats.getClick();
    }

    public void update(int hour, int request, int response, int click) {
        this.hour = hour;
        this.request = request;
        this.response = response;
        this.click = click;
    }
}