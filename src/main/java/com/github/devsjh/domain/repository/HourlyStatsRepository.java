package com.github.devsjh.domain.repository;

import com.github.devsjh.domain.model.DailyStats;
import com.github.devsjh.domain.model.HourlyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourlyStatsRepository extends JpaRepository<HourlyStats, Long> {

    HourlyStats findByDailyStatsAndHour(DailyStats dailyStats, int hour);
}