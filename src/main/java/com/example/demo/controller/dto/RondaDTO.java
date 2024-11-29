package com.example.demo.controller.dto;


import com.example.demo.modelo.ronda.Ronda;

import java.util.List;

public record RondaDTO (String letrasEquivocadas, String palabraAdivinando, int intentos){

    public static RondaDTO desdeModelo(Ronda ronda) {
        return new RondaDTO(
                ronda.getLetrasEquivocadas(),
                ronda.getPalabraAdivinando(),
                ronda.getIntentos()
        );
    }

//    public Ronda aModelo() {
//        Ronda ronda = new RondaUno()
//        return jugador;
//    }
}
