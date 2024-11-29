package com.example.demo.modelo.ronda;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("Terminada")
@Entity
public class RondaFinalizada extends Ronda {

    @Override
    public Ronda proximaRonda() {
        return null;
    }
}
