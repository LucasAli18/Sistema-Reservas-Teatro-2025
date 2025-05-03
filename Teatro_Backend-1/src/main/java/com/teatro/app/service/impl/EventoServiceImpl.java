package com.teatro.app.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import com.teatro.app.repository.EventoRepository;
import com.teatro.app.repository.ReservaRepository;
import com.teatro.app.service.EmailService;
import com.teatro.app.service.EventoService;

import jakarta.transaction.Transactional;

@Service
public class EventoServiceImpl implements EventoService {

    private final EmailService emailService;
    private final ReservaRepository reservaRepository;
    private final EventoRepository eventoRepository;

    public EventoServiceImpl(EventoRepository eventoRepo, EmailService emailService, ReservaRepository reservaRepository) {
        this.eventoRepository = eventoRepo;
        this.emailService = emailService;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Evento crearEvento(Evento evento) {
        if (evento.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del evento no puede ser anterior a la actual.");
        }
        evento.setReservasRealizadas(0);
        return eventoRepository.save(evento);
    }

    @Override
    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    @Override
    public Optional<Evento> buscarPorId(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    public Evento editarEvento(Long id, Evento eventoActualizado) {
        Evento evento = eventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        evento.setNombre(eventoActualizado.getNombre());
        if(!eventoActualizado.getFecha().isBefore(LocalDate.now())) {
        	evento.setFecha(eventoActualizado.getFecha());        	
        }else {
        	evento.setFecha(LocalDate.now());
        }
        evento.setHora(eventoActualizado.getHora());
        evento.setCapacidadMaxima(eventoActualizado.getCapacidadMaxima());
        if(evento.getReservasRealizadas()==0) {
        	evento.setTipoEvento(eventoActualizado.getTipoEvento());        	
        }
        return eventoRepository.save(evento);
    }

    @Override
    @Transactional
    public void eliminarEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        List<Reserva> reservas = reservaRepository.findByEvento(evento);

        for (Reserva reserva : reservas) {
            emailService.enviarAvisoCancelacionEvento(reserva.getCliente(), evento);
            reserva.getCliente().setEventosParticipados(Math.max(0, reserva.getCliente().getEventosParticipados() - 1));
        }

        reservaRepository.deleteAll(reservas);
        eventoRepository.delete(evento);
    }
}
