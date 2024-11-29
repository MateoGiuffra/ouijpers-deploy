package com.example.demo.dao;

import com.example.demo.modelo.Jugador;
import reactor.core.publisher.Mono;

public interface JugadorDAO {
    Mono<Void> crearJugador(Jugador jugador);
    Mono<Jugador> recuperarJugador(String nombre);
    Mono<Jugador> actualizarJugador(Jugador jugador);
    void borrarJugador(String nombre);
    Mono<Integer> obtenerPuntaje(String nombre);
}
