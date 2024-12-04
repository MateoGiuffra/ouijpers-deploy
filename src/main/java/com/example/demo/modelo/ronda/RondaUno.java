package com.example.demo.modelo.ronda;

import com.example.demo.modelo.RandomizerEspiritual;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

@DiscriminatorValue("Primer")
@Entity
public class RondaUno extends Ronda{

    public RondaUno(){
        super();
    }

    public RondaUno(RandomizerEspiritual random){
        super();
        this.palabrasPosibles = List.of(
                "alma", "espiritu", "sombra", "fantasma", "bruja",
                "hechizo", "duende", "cripta", "ritual", "miedo",
                "eco", "angel", "llanto", "banshee", "fuego",
                "vudu", "karma", "hueso", "vela", "cuervo",
                "grito", "truco", "magia", "cirio", "luz"
        );
        this.setRandomizer(random);
        this.setComienzoDeRonda();
    }

    @Override
    protected void setComienzoDeRonda() {
        this.palabrasPosibles = List.of(
                "alma", "espiritu", "sombra", "fantasma", "bruja",
                "hechizo", "duende", "cripta", "ritual", "miedo",
                "eco", "angel", "llanto", "banshee", "fuego",
                "vudu", "karma", "hueso", "vela", "cuervo",
                "grito", "truco", "magia", "cirio", "luz"
        );
        this.elegirPalabraRandom();
    }


    @Override
    public Ronda proximaRonda() {
        return new RondaDos();
    }
}
