package com.example.demo.dao.impl;

import com.example.demo.dao.JugadorDAO;
import com.example.demo.exception.accionInvalida.JugadorDuplicadoException;
import com.example.demo.exception.notFound.JugadorNoEncontradoException;
import com.example.demo.modelo.Jugador;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class JugadorDAOImpl implements JugadorDAO {

    private final Firestore baseDeDatos;
    private ListenerRegistration registration;

    public JugadorDAOImpl(Firestore baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
        registration = null;
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

    @Override
    public Flux<Jugador> obtenerRanking() {
        return Flux.create(sink -> {
            // Establecemos el listener para los cambios en la colección 'jugadores'
            this.registration = baseDeDatos.collection("jugadores")
                    .orderBy("puntuacion", Query.Direction.DESCENDING)
                    .addSnapshotListener((querySnapshot, e) -> {
                        if (e != null) {
                            // Si ocurre un error, lo manejamos y emitimos el error en el flujo
                            sink.error(e);
                            return;
                        }
                        if (querySnapshot != null) {
                            // Procesamos los documentos y emitimos cada jugador
                            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                                Jugador jugador = document.toObject(Jugador.class);
                                sink.next(jugador);
                            }
                        }
                    });

            // Limpiamos el listener cuando el flujo termine (cuando el cliente deje de escuchar)
            sink.onCancel(() -> {if (registration != null) registration.remove();});
        });
    }


    @Override
    public List<Jugador> obtenerTop() {
        try{
            QuerySnapshot query = baseDeDatos.collection("jugadores").orderBy("puntuacion", Query.Direction.DESCENDING).limit(3).get().get();
            return query.toObjects(Jugador.class);

        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Flux<String> palabraAdivinandoDe(String nombre) {
        return Flux.create(sink -> {
            ListenerRegistration registration = baseDeDatos.collection("jugadores")
                    .whereEqualTo("nombre", nombre)
                    .addSnapshotListener((querySnapshot, e) -> {
                        if (e != null) {
                            // Emitir el error al flujo
                            sink.error(e);
                            return;
                        }
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Procesar el primer documento del resultado
                            DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
                            Jugador jugador = doc.toObject(Jugador.class);
                            sink.next(jugador.getPalabraAdivinando()); // Emitir el objeto jugador
                        }
                    });

            // Eliminar el listener al cancelar la suscripción
            sink.onDispose(registration::remove);
        });
    }

    public void detenerRanking(){
        if (registration != null) {
            registration.remove();
            registration = null; // Para evitar reutilización accidental
        }
    }

}
