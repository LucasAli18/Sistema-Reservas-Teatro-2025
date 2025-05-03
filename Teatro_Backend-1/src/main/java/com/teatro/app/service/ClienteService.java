package com.teatro.app.service;

import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Reserva;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente crearCliente(Cliente cliente);
    void eliminarCliente(String dni);
    Cliente editarCliente(String dni, String nuevoNombre, String nuevoEmail, boolean entradaGratis);
    List<Reserva> obtenerReservasPorCliente(String dni);
    List<Cliente> listarClientes();
    Optional<Cliente> buscarPorDni(String dni);
}

