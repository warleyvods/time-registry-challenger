package com.example.timeregistrychallenger.usecases;

import com.example.timeregistrychallenger.exceptions.CannotAddAlocation;
import com.example.timeregistrychallenger.gateways.AlocationGateway;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Alocation;
import com.example.timeregistrychallenger.models.Beat;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
public record CreateAlocation(BeatGateway beatGateway, DurationCalculate durationCalculate, AlocationGateway alocationGateway) {

    public Alocation execute(final Alocation alocation) {
        final Beat beat = beatGateway.findByDayDate(alocation.getDia());
        final Duration duration = durationCalculate.getDuration(beat);

        if (duration.compareTo(alocation.getTempo()) < 0) {
            throw new CannotAddAlocation("Não pode alocar time maior que o time trabalhado no dia.");
        }

        return alocationGateway.save(alocation);
    }
}
