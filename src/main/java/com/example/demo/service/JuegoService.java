package com.example.demo.service;

import com.example.demo.modelo.Juego;
import com.example.demo.modelo.Jugador;

public interface JuegoService {
    void crearJuego(Juego juego);
    Juego recuperarJuego(Long id);
    void eliminarJuego(Juego juego);
    void actualizarJuego(Juego juego);
    int cantIntentosRestantes(Long id);
    String palabraAdivinando(Long id);
    Jugador empezarJuego(String nombre);
    String letrasEquivocadas(Long id);

    String rondaActual(Long id);
}
