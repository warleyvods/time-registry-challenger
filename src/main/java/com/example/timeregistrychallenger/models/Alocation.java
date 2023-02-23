package com.example.timeregistrychallenger.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Alocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dia;
    private Duration tempo;
    private String nomeProjeto;

    @ManyToOne
    private Beat beat;

}