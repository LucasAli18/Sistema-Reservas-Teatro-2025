package com.teatro.app.controller;

import com.teatro.app.dto.ReservaDTO;
import com.teatro.app.entity.Reserva;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IReservaController {

    @PostMapping
    Reserva crearReserva(@RequestBody ReservaDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<String> eliminarReserva(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada);

    @GetMapping
    List<Reserva> listarReservas();
}
