package com.example.demo.modelo.estadoJuego;
import com.example.demo.modelo.ronda.Ronda;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue("Equivocado")
@NoArgsConstructor
public class Equivocado extends EstadoJuego{
    @Override
    public int cantPuntosObtenidos(Ronda ronda, Character letra) {
        return ronda.letraEquivocada(letra);
    }
}
