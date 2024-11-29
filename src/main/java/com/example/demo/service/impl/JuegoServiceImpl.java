package com.example.demo.service.impl;

import com.example.demo.dao.JuegoDAO;
import com.example.demo.dao.impl.JugadorDAOImpl;
import com.example.demo.modelo.Juego;
import com.example.demo.modelo.Jugador;
import com.example.demo.modelo.ronda.Ronda;
import com.example.demo.service.JuegoService;
import com.example.demo.service.JugadorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.demo.exception.notFound.JuegoNoEncontradoException;
@Service
@Transactional
public class JuegoServiceImpl implements JuegoService {

    private final JuegoDAO juegoDAO;
    private final JugadorService jugadorService;

    public JuegoServiceImpl(JuegoDAO juegoDAO, JugadorDAOImpl jugadorDAO, JugadorService jugadorService) {
        this.juegoDAO = juegoDAO;
        this.jugadorService = jugadorService;
    }

    @Override
    public void crearJuego(Juego juego) {
        juegoDAO.save(juego);
    }

    @Override
    public Juego recuperarJuego(Long id) {
        return juegoDAO.findById(id).orElseThrow(JuegoNoEncontradoException::new);
    }

    @Override
    public void eliminarJuego(Juego juego) {
        juegoDAO.delete(juego);
    }

    @Override
    public void actualizarJuego(Juego juego) {
        juegoDAO.save(juego);
    }

    @Override
    public int cantIntentosRestantes(Long id) {
        return juegoDAO.cantIntentosRestantes(id);
    }

    @Override
    public String palabraAdivinando(Long id) {
        return juegoDAO.palabraAdivinando(id);
    }

    @Override
    public Jugador empezarJuego(String nombreJugador) {
        //crea el juego y lo persiste
        Juego juego = new Juego();
        juegoDAO.save(juego);

        //crea el jugador y lo persiste con el id del juego recien creado
        Jugador jugador = new Jugador(nombreJugador);
        jugadorService.crearJugador(jugador, juego.getId()).subscribe();

        //retorna el jugador
        return jugador;
    }

    @Override
    public String letrasEquivocadas(Long id) {
        return juegoDAO.letrasEquivocadas(id);
    }

    @Override
    public String rondaActual(Long id) {
        Ronda ronda = juegoDAO.rondaActualDe(id);
        return ronda.getClass().getSimpleName();
    }

}
