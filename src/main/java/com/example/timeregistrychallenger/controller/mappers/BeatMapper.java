package com.example.timeregistrychallenger.controller.mappers;

import com.example.timeregistrychallenger.controller.dtos.BeatResponseDTO;
import com.example.timeregistrychallenger.models.Beat;

import java.util.stream.Stream;

public class BeatMapper {

    public static BeatResponseDTO toDTO(final Beat beat) {
        final var hours = Stream.of(
                        beat.getFirstHour(),
                        beat.getSecondHour(),
                        beat.getThirdHour(),
                        beat.getFourthHour())
                .map(hora -> hora != null ? hora.toLocalTime() : null)
                .toList();
        return new BeatResponseDTO(beat.getDayDate(), hours);
    }

}
