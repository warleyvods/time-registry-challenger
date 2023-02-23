package com.example.timeregistrychallenger.gateways;

import com.example.timeregistrychallenger.models.Alocation;
import com.example.timeregistrychallenger.repository.AlocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlocationGateway {

    private final AlocationRepository alocationRepository;

    public Alocation save(Alocation alocation) {
        return alocationRepository.save(alocation);
    }
}
