package com.example.demo.controller.rest;

import com.example.demo.controller.dto.*;
import com.example.demo.controller.utils.Validator;
import com.example.demo.service.JuegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.modelo.Jugador;

@RestController
@CrossOrigin
@RequestMapping("/juego")
public class JuegoREST {

    private final JuegoService juegoService;

    public JuegoREST(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping()
    public ResponseEntity<JugadorDTO> empezarJuego(@RequestBody String nombre) {
        Validator.getInstance().validarJugador(nombre);
        Jugador jugador = juegoService.empezarJuego(nombre);
        return ResponseEntity.ok(JugadorDTO.desdeModelo(jugador));
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




}
