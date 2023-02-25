package com.example.timeregistrychallenger.controller.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDate;

public record AlocationsRequestDTO(
        @JsonProperty("dia")
        LocalDate day,
        @JsonProperty("tempo")
        Duration time,
        @JsonProperty("nomeProjeto")
        String projectName
) {
}
