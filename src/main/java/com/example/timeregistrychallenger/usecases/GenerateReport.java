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
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
@RequiredArgsConstructor
public class GenerateReport {

    private final BeatGateway beatGateway;
    private final DurationCalculate durationCalculate;
    private final AlocationMapper alocationMapper;

    public ReportResponseDTO execute(final LocalDate date) {
        final String month = date.format(ofPattern("yyyy-MM"));
        final List<Beat> beats = beatGateway.findByCustomDate(date.getMonthValue(), date.getYear());

        final var alocationList = beats.stream()
                .flatMap(beat -> beat.getAlocations().stream())
                .map(alocationMapper::toDto)
                .toList();

        final List<BeatResponseDTO> beatResponseDTOS = beats.stream().map(BeatMapper::toDTO).toList();

        final Duration totalHours = getDurationMonth(beats);
        final DaysOfMonth laborDays = getLaborDays(date);
        final Integer maxHoursMonth = getMaxHoursMonth(laborDays);

        final Duration maxDurationHoursMonth = Duration.ofHours(maxHoursMonth);

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

    private Duration getDurationMonth(List<Beat> beatList) {
        return beatList.stream()
                .map(durationCalculate::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private Integer getMaxHoursMonth(DaysOfMonth daysOfMonth) {
        int totalSaturday = daysOfMonth.saturdays() * 4;
        int totalWeek = daysOfMonth.workdays() * 8;
        return totalSaturday + totalWeek;
    }

    private DaysOfMonth getLaborDays(LocalDate startDate) {
        LocalDate start = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        int workdays = 0;
        int saturday = 0;
        for (LocalDate date = start; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                saturday++;
            }

            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workdays++;
            }
        }

        return new DaysOfMonth(workdays, saturday);
    }

    record DaysOfMonth(Integer workdays, Integer saturdays) {}
}
