package com.teatro.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import com.teatro.app.entity.enums.EstadoPago;
import com.teatro.app.exception.ClienteNoEncontradoException;
import com.teatro.app.repository.ClienteRepository;
import com.teatro.app.repository.EventoRepository;
import com.teatro.app.repository.ReservaRepository;
import com.teatro.app.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

    private final EventoRepository eventoRepository;
    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ReservaRepository reservaRepository, EventoRepository eventoRepository) {
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
        this.eventoRepository = eventoRepository;
    }
    @Override
    public Cliente crearCliente(Cliente cliente) {
        cliente.setEventosParticipados(0);
        cliente.setTieneEntradaGratis(false);
        return clienteRepository.save(cliente);
    }
    @Override //Lo utilizo porque me parece que dependen de varias entityes x lo que puede errar en el medio
    public void eliminarCliente(String dni) {
        Cliente cliente = clienteRepository.findById(dni)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Reserva> reservas = reservaRepository.findByClienteDni(dni);

        for (Reserva r : reservas) {
            if (r.getEstadoPago() == EstadoPago.NO_PAGADO) {
                Evento evento = r.getEvento();
                int actuales = evento.getReservasRealizadas();
                evento.setReservasRealizadas(Math.max(0, actuales - 1));  // prevenir negativo
                eventoRepository.save(evento);
                reservaRepository.delete(r);
            }
        }
        clienteRepository.delete(cliente);
    }
    
    @Override
    public List<Reserva> obtenerReservasPorCliente(String dni) {
        List<Reserva> reservas = reservaRepository.findByClienteDni(dni);
        return reservas;
    }
    @Override
    public Cliente editarCliente(String dni, String nuevoNombre, String nuevoEmail, boolean entradaGratis) {
        Cliente cliente = clienteRepository.findById(dni)
            .orElseThrow(() -> new ClienteNoEncontradoException(dni));

        if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
            cliente.setNombre(nuevoNombre);
        }
        
        if (nuevoEmail != null && !nuevoEmail.isEmpty()) {
            cliente.setEmail(nuevoEmail);
        }
        cliente.setTieneEntradaGratis(entradaGratis);
        return clienteRepository.save(cliente);
    }
    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
    @Override
    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findById(dni);
    }

}
