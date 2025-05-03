package com.teatro.app.event;

import org.springframework.context.ApplicationEvent;

import com.teatro.app.entity.Reserva;

public class ReservaCreadaEvent extends ApplicationEvent {
    private final Reserva reserva;

    public ReservaCreadaEvent(Object source, Reserva reserva) {
        super(source);
        this.reserva = reserva;
    }

    public Reserva getReserva() {
        return reserva;
    }
}
