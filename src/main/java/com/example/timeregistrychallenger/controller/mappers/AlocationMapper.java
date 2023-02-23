package com.example.timeregistrychallenger.controller.mappers;

import com.example.timeregistrychallenger.controller.dtos.AlocationResponseDTO;
import com.example.timeregistrychallenger.controller.dtos.AlocationsRequestDTO;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Alocation;
import com.example.timeregistrychallenger.models.Beat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlocationMapper {

    private final BeatGateway beatGateway;

    public Alocation toModel(AlocationsRequestDTO alocationsRequestDTO) {
        final Beat beat = beatGateway.findByDayDate(alocationsRequestDTO.dia());
        Alocation alocation = new Alocation();
        alocation.setBeat(beat);
        alocation.setDia(alocationsRequestDTO.dia());
        alocation.setTempo(alocationsRequestDTO.tempo());
        alocation.setNomeProjeto(alocationsRequestDTO.nomeProjeto());
        return alocation;
    }

    public AlocationResponseDTO toDto(Alocation alocation) {
        return new AlocationResponseDTO(alocation.getNomeProjeto(), alocation.getTempo());
    }

}
