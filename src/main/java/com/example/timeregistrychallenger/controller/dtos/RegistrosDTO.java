package com.example.timeregistrychallenger.controller.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RegistrosDTO(
        LocalDate dia,
        List<LocalTime> horarios
) {
}
