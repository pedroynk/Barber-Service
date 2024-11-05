package com.ndbarbearia.barberservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum ServiceEnum {
    CORTEMAQUINA(1, "Corte Máquina"),
    CORTETESOURA(2, "Corte Tesoura"),
    SOBRANCELHA(3, "Sobrancelha"),
    BARBA(4, "Barba");

    private int service_id;
    private String service_description;
}
