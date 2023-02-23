package com.example.timeregistrychallenger.usecases;

import com.example.timeregistrychallenger.models.Beat;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DurationCalculate {

    public Duration getDuration(Beat beat) {
        Duration duration = Duration.ZERO;
        if (beat.getFirstHour() != null && beat.getSecondHour() != null) {
            duration = duration.plus(Duration.between(beat.getFirstHour(), beat.getSecondHour()));
        }
        if (beat.getThirdHour() != null && beat.getFourthHour() != null) {
            duration = duration.plus(Duration.between(beat.getThirdHour(), beat.getFourthHour()));
        }
        return duration;
    }
}
