package com.example.demo.controller.rest;

import com.example.demo.controller.dto.*;
import com.example.demo.controller.utils.Validator;
import com.example.demo.modelo.Juego;
import com.example.demo.service.JuegoService;
import com.example.demo.service.impl.JugadorServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.modelo.Jugador;
@RestController
@CrossOrigin
@RequestMapping("/juego")
public class JuegoREST {

    private final JuegoService juegoService;
    private final JugadorServiceImpl jugadorServiceImpl;

    public JuegoREST(JuegoService juegoService, JugadorServiceImpl jugadorServiceImpl) {
        this.juegoService = juegoService;
        this.jugadorServiceImpl = jugadorServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<JugadorDTO> empezarJuego(@RequestBody String nombre) {
        Validator.getInstance().validarJugador(nombre);
        Jugador jugador = juegoService.empezarJuego(nombre);
        return ResponseEntity.ok(JugadorDTO.desdeModelo(jugador));
    }

    @PutMapping("/{id}/avanzarRonda")
    public ResponseEntity<String> pasarALaSiguienteRonda(@PathVariable Long id) {
        juegoService.pasarALaSiguienteRonda(id);
        return ResponseEntity.ok("Se ha pasado correctamente a la siguiente ronda");
    }

    @GetMapping("/{id}/palabraAdivinando")
    public ResponseEntity<String> obtenerPalabraAdivinando(@PathVariable Long id) {
        String palabra = juegoService.palabraAdivinando(id);
        return ResponseEntity.ok(palabra);
    }
    @GetMapping("/{id}/cantidadDeIntentos")
    public ResponseEntity<Integer> obtenerCantidadDeIntentos(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.cantIntentosRestantes(id));
    }

    @GetMapping("/{id}/letrasEquivocadas")
    public ResponseEntity<String> letrasEquivocadas(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.letrasEquivocadas(id));
    }


    @GetMapping("/{id}/rondaActual")
    public ResponseEntity<String> rondaActual(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.rondaActual(id));
    }

    @PutMapping("/{id}/empezarRondaUltimate")
    public ResponseEntity<JuegoDTO> empezarRondaUltimate(@RequestBody NombresDTO nombres, @PathVariable Long id) {
        Validator.getInstance().validarNombres(nombres);
        Validator.getInstance().validarIdDeJuego(id);

        Jugador j1 = jugadorServiceImpl.buscarJugador(nombres.nombreJ1()).block();
        Jugador j2 = jugadorServiceImpl.buscarJugador(nombres.nombreJ2()).block();
        Jugador j3 = jugadorServiceImpl.buscarJugador(nombres.nombreJ3()).block();

        Juego juego = juegoService.empezarRondaUltimate(j1,j2,j3,id);
        return ResponseEntity.ok(JuegoDTO.desdeModelo(juego));
    }



}
