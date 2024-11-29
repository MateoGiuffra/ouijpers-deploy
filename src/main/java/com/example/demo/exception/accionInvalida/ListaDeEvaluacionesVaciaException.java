package com.example.demo.exception.accionInvalida;

public class ListaDeEvaluacionesVaciaException extends AccionInvalidaException {

    @Override
    public String getMessage() {
        return "La lista de evaluaciones esta vacia";
    }
}
