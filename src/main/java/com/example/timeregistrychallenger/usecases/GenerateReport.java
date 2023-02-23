package com.example.timeregistrychallenger.usecases;

import com.example.timeregistrychallenger.controller.dtos.BeatResponseDTO;
import com.example.timeregistrychallenger.controller.dtos.ReportResponseDTO;
import com.example.timeregistrychallenger.controller.mappers.AlocationMapper;
import com.example.timeregistrychallenger.controller.mappers.BeatMapper;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Beat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
@RequiredArgsConstructor
public class GenerateReport {
    private final BeatGateway beatGateway;
    private final DurationCalculate durationCalculate;
    private final AlocationMapper alocationMapper;

    public ReportResponseDTO execute(LocalDate date) {
        String month = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<Beat> beats = beatGateway.findByCustomDate(date.getMonthValue(), date.getYear());

        var alocationList = beats.stream()
                .flatMap(beat -> beat.getAlocations().stream().map(alocationMapper::toDto))
                .toList();

        List<BeatResponseDTO> beatResponseDTOS = beats.stream()
                .map(BeatMapper::toDTO)
                .toList();

        Duration totalHours = getDurationMonth(beats);
        DaysOfMonth laborDays = getLaborDays(date);
        Integer maxHoursMonth = getMaxHoursMonth(laborDays);
        Duration maxDurationHoursMonth = Duration.ofHours(maxHoursMonth);

        Duration horasDevidas = maxDurationHoursMonth.minus(totalHours);
        if (horasDevidas.isNegative()) {
            horasDevidas = Duration.ZERO;
        }

        Duration horasExcedidas = totalHours.minus(maxDurationHoursMonth);
        if (horasExcedidas.isNegative()) {
            horasExcedidas = Duration.ZERO;
        }

        return new ReportResponseDTO(month, totalHours, horasExcedidas, horasDevidas, beatResponseDTOS, alocationList);
    }

    private Duration getDurationMonth(List<Beat> beats) {
        return beats.stream()
                .map(durationCalculate::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Integer getMaxHoursMonth(DaysOfMonth daysOfMonth) {
        return daysOfMonth.workdays() * 8 + daysOfMonth.saturdays() * 4;
    }

    private DaysOfMonth getLaborDays(LocalDate date) {
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        int workdays = 0;
        int saturdays = 0;

        for (LocalDate day = firstDayOfMonth; !day.isAfter(lastDayOfMonth); day = day.plusDays(1)) {
            DayOfWeek dayOfWeek = day.getDayOfWeek();

            if (dayOfWeek == DayOfWeek.SATURDAY) {
                saturdays++;
            } else if (dayOfWeek != DayOfWeek.SUNDAY) {
                workdays++;
            }
        }

        return new DaysOfMonth(workdays, saturdays);
    }

    record DaysOfMonth(int workdays, int saturdays) {}
}
