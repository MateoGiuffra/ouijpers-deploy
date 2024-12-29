package com.example.demo.controller.dto;


import com.example.demo.modelo.Juego;

public record JuegoDTO (RondaDTO rondaDTO){

    public static JuegoDTO desdeModelo(Juego juego) {
        return new JuegoDTO(
                RondaDTO.desdeModelo(juego.getRondaActual())
        );
    }

    public Juego aModelo() {
        return new Juego();
    }
}
