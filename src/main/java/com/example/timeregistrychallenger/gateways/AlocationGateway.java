package com.example.timeregistrychallenger.gateways;

import com.example.timeregistrychallenger.models.Alocation;
import com.example.timeregistrychallenger.repository.AlocationRepository;
import org.springframework.stereotype.Service;

@Service
public record AlocationGateway(AlocationRepository alocationRepository) {

    public Alocation save(final Alocation alocation) {
        return alocationRepository.save(alocation);
    }
}
