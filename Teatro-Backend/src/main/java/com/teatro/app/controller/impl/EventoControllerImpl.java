package com.teatro.app.controller.impl;

import com.teatro.app.controller.IEventoController;
import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import com.teatro.app.entity.enums.EstadoPago;
import com.teatro.app.service.EventoService;
import com.teatro.app.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoControllerImpl implements IEventoController {

    private final EventoService eventoService;
    private final ReservaService reservaService;

    public EventoControllerImpl(EventoService eventoService, ReservaService reservaService) {
        this.eventoService = eventoService;
        this.reservaService = reservaService;
    }

    @Override
    public Evento crearEvento(@RequestBody Evento evento) {
        return eventoService.crearEvento(evento);
    }

    @Override
    public List<Reserva> obtenerReservasDelEvento(@PathVariable Long id) {
        Evento evento = eventoService.buscarPorId(id).orElseThrow();
        return reservaService.buscarPorEvento(evento);
    }

    @Override
    public ResponseEntity<Evento> editarEvento(@PathVariable Long id, @RequestBody Evento evento) {
        Evento actualizado = eventoService.editarEvento(id, evento);
        return ResponseEntity.ok(actualizado);
    }

    @Override
    public ResponseEntity<String> eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.ok("Evento eliminada/o correctamente.");
    }

    @Override
    public ResponseEntity<Evento> getEvento(@PathVariable Long id) {
        Evento evento = eventoService.buscarPorId(id).orElseThrow();
        return ResponseEntity.ok(evento);
    }

    @Override
    public ResponseEntity<Void> actualizarEstadoReserva(@PathVariable Long id, @RequestBody String nuevoEstado) {
        String limpio = nuevoEstado.replace("\"", "").trim();
        reservaService.actualizarEstado(id, EstadoPago.valueOf(limpio));
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<Evento> listarEventos() {
        return eventoService.listarEventos();
    }
}
