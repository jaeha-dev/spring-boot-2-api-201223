package com.github.devsjh.domain.repository;

import com.github.devsjh.domain.model.DailyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStatsRepository extends JpaRepository<DailyStats, Long> {

    DailyStats findByDate(String date);
}