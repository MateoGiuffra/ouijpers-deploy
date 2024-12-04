package com.example.demo.modelo;

import com.example.demo.modelo.ronda.Ronda;
import com.example.demo.modelo.ronda.RondaUno;
import jakarta.persistence.*;
import lombok.*;


@Getter @Setter @ToString @EqualsAndHashCode
@Entity
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ronda_actual_id")
    private Ronda rondaActual;

    public Juego() {
        this.rondaActual = new RondaUno();
    }

    public Juego(Ronda ronda) {
        this.rondaActual = ronda;
    }

    public int evaluarLetra(Character letra, Jugador jugador){
        int puntaje = rondaActual.evaluarLetra(letra, jugador);
        if (rondaActual.getIntentos() == 0 || rondaActual.esPalabraAcertada()){
          this.cambiarProximaRonda();
        }
        return puntaje;
    }

    public void cambiarProximaRonda() {
        rondaActual = rondaActual.proximaRonda();
    }

    public void cambiarTurnoA(Jugador jugador, Jugador jugadorSiguiente) {
        this.rondaActual.cambiarTurnoA(jugador, jugadorSiguiente);
    }

    public String getLetrasEquivocadas(){
        return rondaActual.getLetrasEquivocadas();
    }

    public String getPalabraAAdivinar() {
        return rondaActual.getPalabraAAdivinar();
    }

    public int getIntentos() {
        return rondaActual.getIntentos();
    }

    public String getLetrasUsadas() {
        return rondaActual.getLetrasEquivocadas();
    }


}
