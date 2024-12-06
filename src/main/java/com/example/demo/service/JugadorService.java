package com.example.demo.service;

import com.example.demo.modelo.Juego;
import com.example.demo.modelo.Jugador;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface JugadorService {
    Mono<Void> crearJugador(Jugador jugador, Long id);
    Mono<Jugador> buscarJugador(String nombre);
    void borrarJugador(String nombre);
    Mono<Jugador> actualizar(Jugador jugador);
    Flux<Jugador> obtenerRanking();
    Mono<Jugador> adivinarLetra(Jugador jugador, Character letra, Juego juego);
    Mono<Integer> obtenerPuntaje(String nombre);
    void detenerRanking();
    List<Jugador> obtenerTop();
    Flux<String> palabraAdivinandoDe(String nombre);
}
