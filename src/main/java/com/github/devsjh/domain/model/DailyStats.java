package com.github.devsjh.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "DAILY_STATS")
public class DailyStats {

    @Id
    @Column(name = "DAILY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date; // 집계 날짜 정보 (ex. 2020-01-01)

    @JsonIgnore
    @OneToMany(mappedBy = "dailyStats", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HourlyStats> hourlyStatsList;

    @Builder
    public DailyStats(Long id, String date, List<HourlyStats> hourlyStatsList) {
        this.id = id;
        this.date = date;
        this.hourlyStatsList = hourlyStatsList;
    }

    public void addHourlyStats(HourlyStats hourlyStats) {
        this.hourlyStatsList.add(hourlyStats.getId(), hourlyStats);

        hourlyStats.setDailyStats(this);
    }

    public void update(String date) {
        this.date = date;
    }

    // public void update(String date, List<HourlyStats> hourlyStatsList) {
    //     this.date = date;
    //     this.hourlyStatsList = hourlyStatsList;
    // }
}