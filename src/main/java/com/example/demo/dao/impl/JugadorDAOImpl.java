package com.example.demo.dao.impl;

import com.example.demo.dao.JugadorDAO;
import com.example.demo.exception.accionInvalida.JugadorDuplicadoException;
import com.example.demo.exception.notFound.JugadorNoEncontradoException;
import com.example.demo.modelo.Jugador;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Repository
public class JugadorDAOImpl implements JugadorDAO {

    private final Firestore baseDeDatos;

    public JugadorDAOImpl(Firestore baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public Mono<Void> crearJugador(Jugador jugador) {
        // Verifica si el jugador ya existe en la base de datos
        ApiFuture<DocumentSnapshot> future = baseDeDatos.collection("jugadores")
                .document(jugador.getNombre()).get();

        return Mono.fromCallable(future::get)
                .flatMap(document -> {
                    if (document.exists()) {
                        return Mono.error(new JugadorDuplicadoException(jugador.getNombre()));
                    } else {
                        ApiFuture<WriteResult> createFuture = baseDeDatos.collection("jugadores")
                                .document(jugador.getNombre()).set(jugador);
                        return Mono.fromCallable(createFuture::get).then();
                    }
                });
    }

    @Override
    public Mono<Jugador> recuperarJugador(String nombre) {
        ApiFuture<DocumentSnapshot> future = baseDeDatos.collection("jugadores").document(nombre).get();

        return Mono.fromCallable(future::get)
                .flatMap(document -> {
                    if (!document.exists()) return Mono.error(new JugadorNoEncontradoException(nombre));
                    Jugador jugador = document.toObject(Jugador.class);
                    return Mono.just(jugador);
                });
    }

    @Override
    public Mono<Jugador> actualizarJugador(Jugador jugador) {
        ApiFuture<WriteResult> future = baseDeDatos.collection("jugadores").document(jugador.getNombre()).set(jugador);

        return Mono.fromCallable(future::get) // Ejecutamos la llamada de forma bloqueante
                .flatMap(writeResult -> {
                    // Si la operación se completó exitosamente, devolvemos el jugador actualizado
                    return Mono.just(jugador);
                });
    }

    @Override
    public void borrarJugador(String nombre) {
        try{
            baseDeDatos.collection("jugadores").document(nombre).delete().get();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    @Override
    public Mono<Integer> obtenerPuntaje(String nombre) {
        ApiFuture<DocumentSnapshot> future = baseDeDatos.collection("jugadores")
                .document(nombre).get();

        return Mono.fromCallable(future::get)
                .flatMap(document -> {
                    if (!document.exists()) return Mono.error(new JugadorNoEncontradoException(nombre));
                    // Convertimos el documento a un objeto Jugador y obtenemos su puntuación
                    Jugador jugador = document.toObject(Jugador.class);
                    return Mono.just(jugador.getPuntuacion());
                });
    }

}
