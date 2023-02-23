package com.example.timeregistrychallenger.usecases;

import com.example.timeregistrychallenger.controller.dtos.AlocationsRequestDTO;
import com.example.timeregistrychallenger.controller.mappers.AlocationMapper;
import com.example.timeregistrychallenger.exceptions.CannotAddAlocation;
import com.example.timeregistrychallenger.gateways.AlocationGateway;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Alocation;
import com.example.timeregistrychallenger.models.Beat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
@RequiredArgsConstructor
public class CreateAlocation {

    private final BeatGateway beatGateway;
    private final DurationCalculate durationCalculate;
    private final AlocationGateway alocationGateway;

    public Alocation execute(Alocation alocation) {
        final Beat beat = beatGateway.findByDayDate(alocation.getDia());
        final Duration duration = durationCalculate.getDuration(beat);

        if (duration.compareTo(alocation.getTempo()) < 0) {
            throw new CannotAddAlocation("Não pode alocar tempo maior que o tempo trabalhado no dia");
        }

        return alocationGateway.save(alocation);
    }
}
