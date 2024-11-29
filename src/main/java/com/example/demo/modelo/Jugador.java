package com.example.demo.modelo;

import lombok.*;

@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
public class Jugador {
    private String nombre;
    private int puntuacion;
    private Long idJuego;

    public Jugador(String nombre, Long idJuego) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.idJuego = idJuego;
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
    }

    public void adivinarLetra(Character letra, Juego juego){
        int puntos = juego.evaluarLetra(letra);
        puntuacion += puntos;
    }
}
