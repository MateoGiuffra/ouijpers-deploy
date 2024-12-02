package com.example.demo.controller.rest;

import com.example.demo.controller.dto.JugadorDTO;
import com.example.demo.controller.utils.Validator;
import com.example.demo.modelo.Juego;
import com.example.demo.modelo.Jugador;
import com.example.demo.service.JuegoService;
import com.example.demo.service.JugadorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/jugador")
public class JugadorREST {

    private final JugadorService jugadorService;
    private final JuegoService juegoService;

    public JugadorREST(JugadorService jugadorService, JuegoService juegoService) {
        this.jugadorService = jugadorService;
        this.juegoService = juegoService;
    }

    @PostMapping()
    public ResponseEntity<JugadorDTO> crearJugador(@RequestBody String nombre){
        Validator.getInstance().validarJugador(nombre);
        Jugador jugador = new Jugador(nombre);
        jugadorService.crearJugador(jugador, 123L);
        return ResponseEntity.ok(JugadorDTO.desdeModelo(jugador));
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Mono<JugadorDTO>> buscarJugador(@PathVariable String nombre) {
        Mono<JugadorDTO> jugador = jugadorService.buscarJugador(nombre).map(JugadorDTO::desdeModelo);
        return ResponseEntity.ok(jugador);
    }

    @GetMapping(value = "/ranking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity <Flux<JugadorDTO>> streamRanking() {
        Flux<JugadorDTO> ranking = jugadorService.obtenerRanking()
                .map(JugadorDTO::desdeModelo);
        return ResponseEntity.ok(ranking);
    }

    @PutMapping("/{nombre}/adivinarLetra/{letra}")
    public ResponseEntity<Mono<JugadorDTO>> adivinarLetra(@PathVariable String nombre, @PathVariable Character letra) {
        Validator.getInstance().validarLetra(letra);

        Jugador jugador = jugadorService.buscarJugador(nombre).block();
        Long idJuego = jugador.getIdJuego();

        Validator.getInstance().validarIdDeJuego(idJuego);

        Juego juegoRecuperado = juegoService.recuperarJuego(idJuego);
        jugadorService.adivinarLetra(jugador, letra,juegoRecuperado).subscribe();

        return ResponseEntity.ok(jugadorService.buscarJugador(nombre).map(JugadorDTO::desdeModelo));
    }

    @GetMapping("/{nombre}/puntaje")
    public ResponseEntity<Mono<Integer>> obtenerPuntaje(@PathVariable String nombre) {
        Mono<Integer> puntaje = jugadorService.obtenerPuntaje(nombre);
        return ResponseEntity.ok(puntaje);
    }

    //estos tenemos que eliminar
    @PutMapping("/{nombre}/{puntaje}")
    public ResponseEntity<Mono<JugadorDTO>> actualizar(@PathVariable String nombre, @PathVariable int puntaje) {
        Mono<JugadorDTO> jugadorActualizado = jugadorService.buscarJugador(nombre)
                .flatMap(jugador -> {
                    jugador.setPuntuacion(puntaje);
                    return jugadorService.actualizar(jugador);
                })
                .map(JugadorDTO::desdeModelo);

        return ResponseEntity.ok(jugadorActualizado);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<String> eliminarJugador(@PathVariable String nombre) {
        try {
            jugadorService.borrarJugador(nombre);
            return ResponseEntity.ok("El jugador: " + nombre + " ha sido eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
