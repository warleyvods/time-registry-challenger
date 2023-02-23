package com.example.timeregistrychallenger.controller.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record BeatResponseDTO(
        LocalDate day,
        List<LocalTime> beats
) {
}
