package com.example.timeregistrychallenger.repository;

import com.example.timeregistrychallenger.models.Beat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BeatRepository extends JpaRepository<Beat, Long> {

    @Query(" SELECT be FROM Beat be WHERE MONTH(be.dayDate) = :month AND YEAR(be.dayDate) = :year ")
    List<Beat> findByCustomDate(@Param(value = "month") Integer month, @Param(value = "year") Integer year);

    Beat findByDayDate(LocalDate date);

}
