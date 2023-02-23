package com.example.timeregistrychallenger.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@With
@NoArgsConstructor
@AllArgsConstructor
public class Beat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private LocalDate dayDate;
    private LocalDateTime firstHour;
    private LocalDateTime secondHour;
    private LocalDateTime thirdHour;
    private LocalDateTime fourthHour;

    @OneToMany(mappedBy = "beat", cascade = CascadeType.ALL)
    private List<Alocation> alocations = new ArrayList<>();

    public Beat(LocalDate dayDate, LocalDateTime firstHour) {
        this.dayDate = dayDate;
        this.firstHour = firstHour;
    }
}
