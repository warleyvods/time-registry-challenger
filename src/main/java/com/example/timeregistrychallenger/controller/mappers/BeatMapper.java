package com.example.timeregistrychallenger.controller.mappers;

import com.example.timeregistrychallenger.controller.dtos.responses.BeatResponseDTO;
import com.example.timeregistrychallenger.models.Beat;
import lombok.NoArgsConstructor;

import static java.util.stream.Stream.of;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class BeatMapper {

    public static BeatResponseDTO toDTO(final Beat beat) {
        final var hours = of(beat.getFirstHour(), beat.getSecondHour(), beat.getThirdHour(), beat.getFourthHour())
                .map(hora -> hora != null ? hora.toLocalTime() : null)
                .toList();
        return new BeatResponseDTO(beat.getDayDate(), hours);
    }
}
