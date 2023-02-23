package com.example.timeregistrychallenger.usecases;

import com.example.timeregistrychallenger.exceptions.ExistsHoursException;
import com.example.timeregistrychallenger.exceptions.MaximumHoursException;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Beat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class BeatRegistry {

    private final BeatGateway beatGateway;

    public Beat execute(LocalDateTime date) {
        final LocalDate fromDate = date.toLocalDate();
        final Beat beat = Optional.ofNullable(beatGateway.findByDayDate(fromDate))
                .map(beatFounded -> setBeatHour(beatFounded, date))
                .orElseGet(() -> beatGateway.saveBeat(new Beat(fromDate, date)));

        return beatGateway.saveBeat(beat);
    }

    private Beat setBeatHour(Beat beatFounded, LocalDateTime date) {
        final var hours = Arrays.asList(
                beatFounded.getFirstHour(),
                beatFounded.getSecondHour(),
                beatFounded.getThirdHour(),
                beatFounded.getFourthHour());

        if (hours.contains(date)) {
            throw new ExistsHoursException("Horários já registrados");
        }

        if (hours.stream().anyMatch(Objects::isNull)) {
            int index = hours.indexOf(null);
            return switch (index) {
                case 0 -> beatFounded.withFirstHour(date);
                case 1 -> beatFounded.withSecondHour(date);
                case 2 -> beatFounded.withThirdHour(date);
                case 3 -> beatFounded.withFourthHour(date);
                default -> null;
            };
        }

        throw new MaximumHoursException("Apenas 4 horários podem ser registrados por dia");
    }
}
