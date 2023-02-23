package com.example.timeregistrychallenger.controller.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BeatRequestDTO(
        @NotNull
        LocalDateTime hour
) {
}
