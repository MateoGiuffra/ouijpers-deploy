package com.example.demo.modelo.ronda;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;

@DiscriminatorValue("Segunda")
@Entity
public class RondaDos extends Ronda{

    public RondaDos(){
        super();
    }

    @Override
    protected void setComienzoDeRonda(){
        this.palabrasPosibles =  List.of(
                "aleluyan", "maleficio", "endemoniado", "sanlamuerte", "invocacion",
                "trasluz", "antropofagia", "sacramento", "entrecruzado", "necrofago",
                "aberracion", "luzmala", "profecia", "zahir", "oropel",
                "espectro", "encarnacion", "horripilante", "panteon", "espanto",
                "transfiguracion", "ahorcado", "devorador", "transmutacion", "pergamino"
        );
        this.elegirPalabraRandom();
    }


    @Override
    public Ronda proximaRonda() {
        return new RondaTres();
    }
}
