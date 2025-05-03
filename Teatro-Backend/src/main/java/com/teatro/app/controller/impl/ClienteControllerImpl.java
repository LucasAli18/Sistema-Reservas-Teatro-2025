package com.teatro.app.controller.impl;

import com.teatro.app.controller.IClienteController;
import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Reserva;
import com.teatro.app.entity.enums.EstadoPago;
import com.teatro.app.service.ClienteService;
import com.teatro.app.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteControllerImpl implements IClienteController {

    private final ClienteService clienteService;
    private final ReservaService reservaService;

    public ClienteControllerImpl(ClienteService clienteService, ReservaService reservaService) {
        this.clienteService = clienteService;
        this.reservaService = reservaService;
    }

    @Override
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);
    }

    @Override
    public ResponseEntity<List<Reserva>> obtenerReservasPorCliente(@PathVariable String dni) {
        return ResponseEntity.ok(clienteService.obtenerReservasPorCliente(dni));
    }

    @Override
    public ResponseEntity<Void> eliminarCliente(@PathVariable String dni) {
        clienteService.eliminarCliente(dni);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable String dni, @RequestBody Cliente nuevoCliente) {
        Cliente actualizado = clienteService.editarCliente(
                dni,
                nuevoCliente.getNombre(),
                nuevoCliente.getEmail(),
                nuevoCliente.isTieneEntradaGratis()
        );
        return ResponseEntity.ok(actualizado);
    }

    @Override
    public ResponseEntity<Void> actualizarEstadoReserva(@PathVariable Long id, @RequestBody String nuevoEstado) {
        String limpio = nuevoEstado.replace("\"", "").trim();
        reservaService.actualizarEstado(id, EstadoPago.valueOf(limpio));
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }
}
