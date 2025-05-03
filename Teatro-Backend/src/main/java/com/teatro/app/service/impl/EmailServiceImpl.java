package com.teatro.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import com.teatro.app.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void enviarAvisoDePago(Reserva reserva) {
        logger.info("📩 Enviando email a {} por evento {} el día {}. Estado de pago: {}",
                reserva.getCliente().getEmail(),
                reserva.getEvento().getNombre(),
                reserva.getEvento().getFecha(),
                reserva.getEstadoPago());
    }

    @Override
    public void enviarFelicidadesEntradaGratis(Cliente cliente) {
        logger.info("🎉 Cliente {} tu próxima entrada es gratis!", cliente.getNombre());
    }

    @Override
    public void enviarAvisoCancelacionEvento(Cliente cliente, Evento evento) {
        logger.info("📧 Email a {}: El evento \"{}\" del {} a las {} fue cancelado.",
                cliente.getEmail(), evento.getNombre(), evento.getFecha(), evento.getHora());
    }
}
