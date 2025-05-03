package com.teatro.app.service;

import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;

public interface EmailService {
    void enviarAvisoDePago(Reserva reserva);
    void enviarFelicidadesEntradaGratis(Cliente cliente);
    void enviarAvisoCancelacionEvento(Cliente cliente, Evento evento);
}
