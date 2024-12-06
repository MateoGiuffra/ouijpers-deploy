package com.example.demo.controller.dto;

import com.example.demo.modelo.Jugador;

public record JugadorDTO (String nombre, boolean esMiTurno, int puntaje, Long idJuego, String jugadorSiguiente, String palabraAdivinando){

    public static JugadorDTO desdeModelo(Jugador jugador) {
        return new JugadorDTO(
                jugador.getNombre(),
                jugador.isEsMiTurno(),
                jugador.getPuntuacion(),
                jugador.getIdJuego(),
                jugador.getJugadorSiguiente(),
                jugador.getPalabraAdivinando()
        );
    }

    public Jugador aModelo() {
        Jugador jugador = new Jugador(this.nombre);
        jugador.setEsMiTurno(this.esMiTurno);
        jugador.setPuntuacion(this.puntaje);
        jugador.setIdJuego(this.idJuego);
        jugador.setJugadorSiguiente(this.jugadorSiguiente);
        jugador.setPalabraAdivinando(this.palabraAdivinando);
        return jugador;
    }
}
