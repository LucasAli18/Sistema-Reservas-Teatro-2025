package com.teatro.app.controller;

import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IEventoController {

    @PostMapping
    Evento crearEvento(@RequestBody Evento evento);

    @GetMapping("/{id}/reservas")
    List<Reserva> obtenerReservasDelEvento(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<Evento> editarEvento(@PathVariable Long id, @RequestBody Evento evento);

    @DeleteMapping("/{id}")
    ResponseEntity<String> eliminarEvento(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<Evento> getEvento(@PathVariable Long id);

    @PutMapping("/{id}/reservas")
    ResponseEntity<Void> actualizarEstadoReserva(@PathVariable Long id, @RequestBody String nuevoEstado);

    @GetMapping
    List<Evento> listarEventos();
}
