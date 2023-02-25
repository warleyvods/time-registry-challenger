package com.example.timeregistrychallenger.controller.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BeatRequestDTO(
        @NotNull
        @JsonProperty("dataHora")
        LocalDateTime hour
) {
}
