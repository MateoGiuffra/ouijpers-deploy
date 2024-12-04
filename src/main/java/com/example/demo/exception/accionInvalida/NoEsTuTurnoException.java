package com.example.demo.exception.accionInvalida;

public class NoEsTuTurnoException extends AccionInvalidaException {
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "No es tu turno";
    }
}
