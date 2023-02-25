package com.example.timeregistrychallenger.controller.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.util.List;

public record ReportResponseDTO(
        @JsonProperty("mes")
        String month,
        @JsonProperty("horasTrabalhadas")
        Duration workedHours,
        @JsonProperty("horasExcedentes")
        Duration surplusHours,
        @JsonProperty("horasDevidas")
        Duration hoursDue,
        @JsonProperty("registros")
        List<BeatResponseDTO> records,
        @JsonProperty("alocacoes")
        List<AlocationResponseDTO> alocations
) {
}
