package com.example.timeregistrychallenger.usecases;

import com.example.timeregistrychallenger.controller.dtos.responses.ReportResponseDTO;
import com.example.timeregistrychallenger.controller.mappers.AlocationMapper;
import com.example.timeregistrychallenger.controller.mappers.BeatMapper;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Beat;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public record GenerateReport(BeatGateway beatGateway, DurationCalculate durationCalculate, AlocationMapper alocationMapper) {

    public ReportResponseDTO execute(final LocalDate date) {
        final var month = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        final var beats = beatGateway.findByCustomDate(date.getMonthValue(), date.getYear());

        final var alocationList = beats.stream()
                .flatMap(beat -> beat.getAlocations().stream().map(alocationMapper::toDto))
                .toList();

        final var beatResponseDTOS = beats.stream()
                .map(BeatMapper::toDTO)
                .toList();

        final var totalHours = getDurationMonth(beats);
        final var laborDays = getLaborDays(date);
        final var maxHoursMonth = getMaxHoursMonth(laborDays);
        final var maxDurationHoursMonth = Duration.ofHours(maxHoursMonth);

        var horasDevidas = maxDurationHoursMonth.minus(totalHours);
        if (horasDevidas.isNegative()) {
            horasDevidas = Duration.ZERO;
        }

        var horasExcedidas = totalHours.minus(maxDurationHoursMonth);
        if (horasExcedidas.isNegative()) {
            horasExcedidas = Duration.ZERO;
        }

        return new ReportResponseDTO(month, totalHours, horasExcedidas, horasDevidas, beatResponseDTOS, alocationList);
    }

    private Duration getDurationMonth(final List<Beat> beats) {
        return beats.stream()
                .map(durationCalculate::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Integer getMaxHoursMonth(final DaysOfMonth daysOfMonth) {
        return daysOfMonth.workdays() * 8 + daysOfMonth.saturdays() * 4;
    }

    private DaysOfMonth getLaborDays(final LocalDate date) {
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

    record DaysOfMonth(int workdays, int saturdays) { }
}
