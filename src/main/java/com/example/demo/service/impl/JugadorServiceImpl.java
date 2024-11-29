package  com.example.demo.service.impl;

import com.example.demo.dao.JuegoDAO;
import com.example.demo.dao.impl.JugadorDAOImpl;
import com.example.demo.exception.accionInvalida.JugadorDuplicadoException;
import com.example.demo.exception.notFound.JugadorNoEncontradoException;
import com.example.demo.modelo.Juego;
import com.example.demo.modelo.Jugador;
import com.example.demo.service.JugadorService;
import com.google.cloud.firestore.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class JugadorServiceImpl implements JugadorService {

    private final Firestore db;
    private final JugadorDAOImpl jugadorDAO;
    private final JuegoDAO juegoDAO;

    public JugadorServiceImpl(Firestore db, JugadorDAOImpl jugadorDAO, JuegoDAO juegoDAO) {
        this.db = db;
        this.jugadorDAO = jugadorDAO;
        this.juegoDAO = juegoDAO;
    }

    @Override
    public Mono<Void> crearJugador(Jugador jugador, Long id) {
        jugador.setIdJuego(id);
        return jugadorDAO.crearJugador(jugador)
                .onErrorResume(JugadorDuplicadoException.class, Mono::error);
    }

    @Override
    public Mono<Jugador> buscarJugador(String nombre) {
        return jugadorDAO.recuperarJugador(nombre).switchIfEmpty(Mono.error(new JugadorNoEncontradoException(nombre)));
    }

// nueva propuesta a tiempo real
    @Override
    public Flux<Jugador> obtenerRanking() {
        return Flux.create(sink -> {
            // Establecemos el listener para los cambios en la colección 'jugadores'
            ListenerRegistration registration = db.collection("jugadores")
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
            sink.onCancel(registration::remove);
        });
    }

    @Override
    public Mono<Jugador> adivinarLetra(Jugador jugador, Character letra, Juego juego) {
        jugador.adivinarLetra(letra, juego);
        juegoDAO.save(juego);
        return actualizar(jugador);
    }

    @Override
    public Mono<Integer> obtenerPuntaje(String nombre) {
        return jugadorDAO.obtenerPuntaje(nombre);
    }

    @Override
    public void borrarJugador(String nombre) {
        jugadorDAO.borrarJugador(nombre);
    }

    @Override
    public Mono<Jugador> actualizar(Jugador jugador) {
        return jugadorDAO.actualizarJugador(jugador)
                .onErrorMap(InterruptedException.class, e -> new RuntimeException("Operación interrumpida", e))
                .onErrorMap(ExecutionException.class, Throwable::getCause);
    }
}

