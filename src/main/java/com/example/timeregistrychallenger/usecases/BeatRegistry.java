package com.example.timeregistrychallenger.usecases;

import com.example.timeregistrychallenger.exceptions.ExistsHoursException;
import com.example.timeregistrychallenger.exceptions.MaximumHoursException;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Beat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

@Component
public record BeatRegistry(BeatGateway beatGateway) {

    public Beat execute(final LocalDateTime date) {
        final Beat beat = ofNullable(beatGateway.findByDayDate(date.toLocalDate()))
                .map(beatFound -> setBeatHour(beatFound, date))
                .orElseGet(() -> beatGateway.saveBeat(new Beat(date.toLocalDate(), date)));

        return beatGateway.saveBeat(beat);
    }

    private Beat setBeatHour(final Beat beatFound, final LocalDateTime time) {
        final var hours = asList(
                beatFound.getFirstHour(),
                beatFound.getSecondHour(),
                beatFound.getThirdHour(),
                beatFound.getFourthHour()
        );

        if (hours.contains(time)) {
            throw new ExistsHoursException("Horários já registrados");
        }

        if (hours.stream().anyMatch(Objects::isNull)) {
            int index = hours.indexOf(null);
            return switch (index) {
                case 0 -> beatFound.withFirstHour(time);
                case 1 -> beatFound.withSecondHour(time);
                case 2 -> beatFound.withThirdHour(time);
                case 3 -> beatFound.withFourthHour(time);
                default -> null;
            };
        }

        throw new MaximumHoursException("Apenas 4 horários podem ser registrados por dia");
    }
}
