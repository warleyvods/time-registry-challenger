package com.example.timeregistrychallenger.controller.mappers;

import com.example.timeregistrychallenger.controller.dtos.responses.AlocationResponseDTO;
import com.example.timeregistrychallenger.controller.dtos.requests.AlocationsRequestDTO;
import com.example.timeregistrychallenger.gateways.BeatGateway;
import com.example.timeregistrychallenger.models.Alocation;
import com.example.timeregistrychallenger.models.Beat;
import org.springframework.stereotype.Component;

@Component
public record AlocationMapper(BeatGateway beatGateway) {

    public Alocation toModel(final AlocationsRequestDTO alocationsRequestDTO) {
        final Beat beat = beatGateway.findByDayDate(alocationsRequestDTO.day());
        Alocation alocation = new Alocation();
        alocation.setBeat(beat);
        alocation.setDia(alocationsRequestDTO.day());
        alocation.setTempo(alocationsRequestDTO.time());
        alocation.setNomeProjeto(alocationsRequestDTO.projectName());
        return alocation;
    }

    public AlocationResponseDTO toDto(final Alocation alocation) {
        return new AlocationResponseDTO(alocation.getNomeProjeto(), alocation.getTempo());
    }
}
