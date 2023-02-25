package com.example.timeregistrychallenger.controller;

import com.example.timeregistrychallenger.controller.dtos.requests.AlocationsRequestDTO;
import com.example.timeregistrychallenger.controller.dtos.requests.BeatRequestDTO;
import com.example.timeregistrychallenger.controller.dtos.responses.AlocationResponseDTO;
import com.example.timeregistrychallenger.controller.dtos.responses.BeatResponseDTO;
import com.example.timeregistrychallenger.controller.dtos.responses.ReportResponseDTO;
import com.example.timeregistrychallenger.controller.mappers.AlocationMapper;
import com.example.timeregistrychallenger.controller.mappers.BeatMapper;
import com.example.timeregistrychallenger.usecases.BeatRegistry;
import com.example.timeregistrychallenger.usecases.CreateAlocation;
import com.example.timeregistrychallenger.usecases.GenerateReport;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static java.time.YearMonth.parse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RegistryController {

    private final BeatRegistry beatRegistry;
    private final GenerateReport generateReport;
    private final CreateAlocation createAlocation;
    private final AlocationMapper alocationMapper;

    @PostMapping("/batidas")
    public BeatResponseDTO beat(@RequestBody @Valid final BeatRequestDTO beatRequestDTO) {
        return BeatMapper.toDTO(beatRegistry.execute(beatRequestDTO.hour()));
    }

    @PostMapping("/alocacoes")
    public AlocationResponseDTO alocations(@RequestBody @Valid final AlocationsRequestDTO alocationsRequestDTO) {
        return alocationMapper.toDto(createAlocation.execute(alocationMapper.toModel(alocationsRequestDTO)));
    }

    @GetMapping("/folhas-de-ponto/{month}")
    public ReportResponseDTO report(@PathVariable final String month) {
        return generateReport.execute(parse(month).atDay(1));
    }
}
