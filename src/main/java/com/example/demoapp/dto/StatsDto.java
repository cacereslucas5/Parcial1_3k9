package com.example.demoapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class StatsDto {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;

    public StatsDto(long countMutantDna, long countHumanDna, double ratio) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
        this.ratio = ratio;
    }
}
