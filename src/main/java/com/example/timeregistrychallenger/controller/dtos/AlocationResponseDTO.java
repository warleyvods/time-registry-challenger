package com.example.timeregistrychallenger.controller.dtos;

import java.time.Duration;

public record AlocationResponseDTO(
        String nomeProjeto,
        Duration tempo
) {
}
