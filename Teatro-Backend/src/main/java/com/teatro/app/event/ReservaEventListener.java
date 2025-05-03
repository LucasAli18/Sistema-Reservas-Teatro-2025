package com.teatro.app.event;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Reserva;
import com.teatro.app.entity.enums.EstadoPago;
import com.teatro.app.exception.ClienteNoEncontradoException;
import com.teatro.app.repository.ReservaRepository;
import com.teatro.app.service.ClienteService;
import com.teatro.app.service.EmailService;

@Component
public class ReservaEventListener {

    private final EmailService emailService;
    private final ClienteService clienteService;
    private final ReservaRepository reservaRepository;

    public ReservaEventListener(EmailService emailService, ClienteService clienteService,
                                 ReservaRepository reservaRepository) {
        this.emailService = emailService;
        this.clienteService = clienteService;
        this.reservaRepository = reservaRepository;
    }

    @EventListener
    public void manejarEvento(ReservaCreadaEvent event) {
        Reserva reserva = event.getReserva();

        if (reserva.getEstadoPago() == EstadoPago.NO_PAGADO &&
            reserva.getEvento().getFecha().isEqual(LocalDate.now().plusDays(2))) {
            emailService.enviarAvisoDePago(reserva);
        }

        String dni = reserva.getCliente().getDni();
        LocalDate haceUnAnio = LocalDate.now().minusYears(1);
        List<Reserva> reservas = reservaRepository.findByClienteDni(dni);
        long cantidad = reservas.stream()
                .filter(reserv -> reserv.getEvento().getFecha().isAfter(haceUnAnio))
                .map(reserv -> reserv.getEvento().getId())
                .distinct()
                .count();
        if (cantidad == 5) {
            Cliente cliente = clienteService.buscarPorDni(dni).orElseThrow(() -> new ClienteNoEncontradoException(dni));
            clienteService.editarCliente(cliente.getDni(), cliente.getNombre(), cliente.getEmail(), true);
            emailService.enviarFelicidadesEntradaGratis(cliente);
        }
    }
}
