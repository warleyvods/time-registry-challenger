package com.example.timeregistrychallenger.gateways;

import com.example.timeregistrychallenger.models.Beat;
import com.example.timeregistrychallenger.repository.BeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeatGateway {

    private final BeatRepository beatRepository;

    public Beat saveBeat(Beat beat) {
        return beatRepository.save(beat);
    }

    public Beat findById(Long id) {
        return beatRepository.findById(id).orElse(null);
    }

    public List<Beat> findByCustomDate(Integer month, Integer year) {
        return beatRepository.findByCustomDate(month, year);
    }

    public Beat findByDayDate(LocalDate date) {
        return beatRepository.findByDayDate(date);
    }
}
