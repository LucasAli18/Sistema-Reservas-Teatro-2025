package com.teatro.app.service;

import com.teatro.app.dto.ReservaDTO;
import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import com.teatro.app.entity.enums.EstadoPago;

import java.util.List;

public interface ReservaService {
    Reserva crearReserva(ReservaDTO reservaDTO);
    Reserva editarReserva(Long idReserva, EstadoPago nuevoEstado, String nuevoTipoEntrada);
    void actualizarEstado(Long reservaId, EstadoPago nuevoEstado);
    List<Reserva> buscarPorEvento(Evento evento);
    void eliminarReserva(Long idReserva);
    List<Reserva> obtenerTodas();
}

