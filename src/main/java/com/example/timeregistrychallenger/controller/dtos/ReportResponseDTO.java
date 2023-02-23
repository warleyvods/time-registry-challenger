package com.example.timeregistrychallenger.controller.dtos;

import java.time.Duration;
import java.util.List;

public record ReportResponseDTO(
        String mes,
        Duration horasTrabalhadas,
        Duration horasExcedentes,
        Duration horasDevidas,
        List<BeatResponseDTO> registros,
        List<AlocationResponseDTO> alocacoes
) {
}
