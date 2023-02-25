package com.example.timeregistrychallenger.controller.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record BeatResponseDTO(
        @JsonProperty("dia")
        LocalDate day,
        @JsonProperty("batidas")
        List<LocalTime> beats
) {
}
