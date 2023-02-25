package com.example.timeregistrychallenger.gateways;

import com.example.timeregistrychallenger.models.Beat;
import com.example.timeregistrychallenger.repository.BeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public record BeatGateway(BeatRepository beatRepository) {

    public Beat saveBeat(final Beat beat) {
        return beatRepository.save(beat);
    }

    public List<Beat> findByCustomDate(final Integer month, final Integer year) {
        return beatRepository.findByCustomDate(month, year);
    }

    public Beat findByDayDate(final LocalDate date) {
        return beatRepository.findByDayDate(date);
    }
}
