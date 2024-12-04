package com.example.demo.controller.utils;

import com.example.demo.controller.dto.NombresDTO;

public class Validator {

    private static volatile Validator instance;
    private static final String NOMBRE_VACIO_O_NULO = "El nombre no puede estar vacio o nulo.";
    private static final String ENERGIA_POSITIVA_O_MENOR_A_100 = "La energia debe ser positiva y menor a 100";

    private Validator() {}

    public static Validator getInstance() {
        if (instance == null) {
            synchronized (Validator.class) {
                if (instance == null) {
                    instance = new Validator();
                }
            }
        }
        return instance;
    }



    private void validarNombre(String nombre){
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
    }

    public void validarJugador(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(NOMBRE_VACIO_O_NULO);
        }
    }

    public void validarLetra(Character letra) {
        if (!Character.isLetter(letra)) {
            throw new IllegalArgumentException("La letra no es valida.");
        }
    }

    public void validarIdDeJuego(Long idJuego) {
        if (idJuego == null) {
            throw new IllegalArgumentException("El id del juego no puede ser nulo.");
        }
    }

    public void validarNombres(NombresDTO nombres) {
        this.validarNombre(nombres.nombreJ1());
        this.validarNombre(nombres.nombreJ2());
        this.validarNombre(nombres.nombreJ3());
    }
}
