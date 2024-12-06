package com.example.demo.modelo;

import com.example.demo.exception.accionInvalida.NoEsTuTurnoException;
import com.example.demo.exception.accionInvalida.RecursionException;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import lombok.*;

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor  @IgnoreExtraProperties
public class Jugador {

    private String nombre;
    private boolean esMiTurno;
    private int puntuacion;
    private Long idJuego;
    private String jugadorSiguiente;
    private String palabraAdivinando;

    public Jugador(String nombre, Long idJuego) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.idJuego = idJuego;
        this.esMiTurno = true;
    }

    public void setJugadorSiguiente(String jugadorNombre){
        if (this.nombre != null && this.nombre.equals(jugadorNombre)) {
            throw new RecursionException(this.nombre, jugadorNombre);
        }
        this.jugadorSiguiente = jugadorNombre;
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.esMiTurno = true;
    }

    public void adivinarLetra(Character letra, Juego juego){
        if (!this.esMiTurno) throw new NoEsTuTurnoException();
        puntuacion += juego.evaluarLetra(letra, this);
    }

    public void cambiarTurno() {
        this.esMiTurno = !this.esMiTurno;
    }

}
