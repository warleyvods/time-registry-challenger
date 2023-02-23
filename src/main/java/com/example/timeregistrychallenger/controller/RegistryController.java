package com.example.timeregistrychallenger.controller;

import com.example.timeregistrychallenger.controller.dtos.*;
import com.example.timeregistrychallenger.controller.mappers.AlocationMapper;
import com.example.timeregistrychallenger.controller.mappers.BeatMapper;
import com.example.timeregistrychallenger.usecases.BeatRegistry;
import com.example.timeregistrychallenger.usecases.CreateAlocation;
import com.example.timeregistrychallenger.usecases.GenerateReport;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RegistryController {

    private final BeatRegistry beatRegistry;
    private final GenerateReport generateReport;
    private final CreateAlocation createAlocation;
    private final AlocationMapper alocationMapper;

    @PostMapping("/batidas")
    public BeatResponseDTO beat(@RequestBody @Valid BeatRequestDTO beatRequestDTO) {
        return BeatMapper.toDTO(beatRegistry.execute(beatRequestDTO.hour()));
    }

    @PostMapping("/alocacoes")
    public AlocationResponseDTO alocations(@RequestBody @Valid AlocationsRequestDTO alocationsRequestDTO) {
        return alocationMapper.toDto(createAlocation.execute(alocationMapper.toModel(alocationsRequestDTO)));
    }

    @GetMapping("/folhas-de-ponto/{mes}")
    public ReportResponseDTO report(@PathVariable String mes) {
        final var date = LocalDate.parse(mes + "-01");
        return generateReport.execute(date);
    }
}
