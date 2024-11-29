package com.example.demo.modelo.ronda;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;

@DiscriminatorValue("Segunda")
@Entity
public class RondaDos extends Ronda{

    public RondaDos(){
        super();
        this.palabrasPosibles = List.of(
                "ouija", "pesadilla", "aparicion", "chaman", "espiritismo",
                "leyenda", "sacrificio", "embrujo", "vision", "susurro",
                "penitente", "entidad", "tarot", "chupacabra", "encantamiento",
                "sepultura", "brujeria", "santeria", "carcajada", "gnomo",
                "mascarada", "lamia", "catacumba", "alquimia", "oraculo"
        );
        this.setComienzoDeRonda();
    }

    @Override
    public Ronda proximaRonda() {
        return new RondaTres();
    }
}
