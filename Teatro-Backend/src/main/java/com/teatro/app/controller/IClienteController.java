package com.teatro.app.controller;

import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Reserva;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IClienteController {

    @PostMapping
    Cliente crearCliente(@RequestBody Cliente cliente);

    @GetMapping("/{dni}/reservas")
    ResponseEntity<List<Reserva>> obtenerReservasPorCliente(@PathVariable String dni);

    @DeleteMapping("/{dni}")
    ResponseEntity<Void> eliminarCliente(@PathVariable String dni);

    @PutMapping("/{dni}")
    ResponseEntity<Cliente> actualizarCliente(@PathVariable String dni, @RequestBody Cliente nuevoCliente);

    @PutMapping("/{id}/reservas")
    ResponseEntity<Void> actualizarEstadoReserva(@PathVariable Long id, @RequestBody String nuevoEstado);

    @GetMapping
    List<Cliente> listarClientes();
}
