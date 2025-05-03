package com.teatro.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.teatro.app.dto.ReservaDTO;
import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Evento;
import com.teatro.app.entity.enums.EstadoPago;
import com.teatro.app.repository.ClienteRepository;
import com.teatro.app.repository.EventoRepository;
import com.teatro.app.repository.ReservaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock private ReservaRepository reservaRepo;
    @Mock private ClienteRepository clienteRepo;
    @Mock private EventoRepository eventoRepo;
    @Mock private ApplicationEventPublisher publisher;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debe lanzar error si no hay lugar")
    void noDebePermitirReservaCuandoEventoEstaLleno() {
        Cliente cliente = new Cliente("123", "Pedro", "pedro@mail.com", 0, false);
        Evento evento = new Evento();
        evento.setId(1L);
        evento.setCapacidadMaxima(1);
        evento.setReservasRealizadas(1);
        
        when(clienteRepo.findById("123")).thenReturn(Optional.of(cliente));
        when(eventoRepo.findById(1L)).thenReturn(Optional.of(evento));
        

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
        	ReservaDTO reserva = new ReservaDTO("123", 1L, "VIP", EstadoPago.PAGADO);
            reservaService.crearReserva(reserva);
        });

        assertEquals("Evento sin disponibilidad", ex.getMessage());
        System.out.println(ex.getMessage());
    }
}
