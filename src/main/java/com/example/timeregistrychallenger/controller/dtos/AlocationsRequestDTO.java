package com.example.timeregistrychallenger.controller.dtos;

import java.time.Duration;
import java.time.LocalDate;

public record AlocationsRequestDTO(
        LocalDate dia,
        Duration tempo,
        String nomeProjeto
) {
}
