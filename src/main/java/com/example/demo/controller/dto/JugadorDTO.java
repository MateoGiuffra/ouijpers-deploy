package com.example.demo.controller.dto;

import com.example.demo.modelo.Jugador;

public record JugadorDTO (String nombre, int puntaje, Long idJuego){

    public static JugadorDTO desdeModelo(Jugador jugador) {
        return new JugadorDTO(
                jugador.getNombre(),
                jugador.getPuntuacion(),
                jugador.getIdJuego()
        );
    }

    public Jugador aModelo() {
        Jugador jugador = new Jugador(this.nombre);
        jugador.setPuntuacion(this.puntaje);
        jugador.setIdJuego(this.idJuego);
        return jugador;
    }
}
