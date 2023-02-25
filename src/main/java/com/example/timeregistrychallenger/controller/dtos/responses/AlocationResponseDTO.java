package com.example.timeregistrychallenger.controller.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;

public record AlocationResponseDTO(
        @JsonProperty("nomeProjeto")
        String projectName,
        @JsonProperty("tempo")
        Duration time
) {
}
