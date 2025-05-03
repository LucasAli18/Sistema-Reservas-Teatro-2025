package com.teatro.app.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.teatro.app.dto.ReservaDTO;
import com.teatro.app.entity.Cliente;
import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import com.teatro.app.entity.enums.EstadoPago;
import com.teatro.app.event.ReservaCreadaEvent;
import com.teatro.app.repository.ClienteRepository;
import com.teatro.app.repository.EventoRepository;
import com.teatro.app.repository.ReservaRepository;
import com.teatro.app.service.ReservaService;

import jakarta.transaction.Transactional;

@Service
public class ReservaServiceImp implements ReservaService {
		
	    private final ReservaRepository reservaRepository;
	    private final ClienteRepository clienteRepository;
	    private final EventoRepository eventoRepository;
	    private final ApplicationEventPublisher eventPublisher;

	    public ReservaServiceImp(ReservaRepository reservaRepository, ClienteRepository clienteRepository,
	                          EventoRepository eventoRepository, ApplicationEventPublisher eventPublisher) {
	        this.reservaRepository = reservaRepository;
	        this.clienteRepository = clienteRepository;
	        this.eventoRepository = eventoRepository;
	        this.eventPublisher = eventPublisher;
	    }
	    @Override
	    public Reserva crearReserva(ReservaDTO reservaDTO) {
	        Cliente cliente = clienteRepository.findById(reservaDTO.getDniCliente()).orElseThrow();
	        Evento evento = eventoRepository.findById(reservaDTO.getIdEvento()).orElseThrow();

	        if (evento.getReservasRealizadas() >= evento.getCapacidadMaxima()) {
	            throw new RuntimeException("Evento sin disponibilidad");
	        }

	        Reserva reserva = new Reserva();
	        reserva.setCliente(cliente);
	        reserva.setEvento(evento);
	        reserva.setFechaReserva(LocalDate.now());
	        reserva.setTipoEntrada(reservaDTO.getTipoEntrada());
	        if (cliente.isTieneEntradaGratis()) {
	        	reserva.setEstadoPago(EstadoPago.PAGADO);
	            cliente.setTieneEntradaGratis(false);
	        }else{
	            reserva.setEstadoPago(reservaDTO.getEstadoPago());
	        }
	        if(reserva.getEstadoPago() == EstadoPago.PAGADO) {
	        	cliente.setEventosParticipados(cliente.getEventosParticipados() + 1);        	        	
	        }
	        evento.setReservasRealizadas(evento.getReservasRealizadas() + 1);
	        
	        clienteRepository.save(cliente);
	        eventoRepository.save(evento);
	        Reserva saved = reservaRepository.save(reserva);

	        eventPublisher.publishEvent(new ReservaCreadaEvent(this, saved));
	        return saved;
	    }
	    @Override
	    public Reserva editarReserva(Long idReserva, EstadoPago nuevoEstado, String nuevoTipoEntrada) {
	        Reserva reserva = reservaRepository.findById(idReserva)
	            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

	        if (nuevoEstado != null) {
	            reserva.setEstadoPago(nuevoEstado);
	        }
	        if (nuevoTipoEntrada != null && !nuevoTipoEntrada.isEmpty()) {
	            reserva.setTipoEntrada(nuevoTipoEntrada);
	        }
	        return reservaRepository.save(reserva);
	    }
	    @Override
	    public void actualizarEstado(Long reservaId, EstadoPago nuevoEstado) {
	        Reserva reserva = reservaRepository.findById(reservaId)
	            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

	        reserva.setEstadoPago(nuevoEstado);
	        Cliente cliente = reserva.getCliente();
	        if(nuevoEstado == EstadoPago.PAGADO) {
	        	cliente.setEventosParticipados(cliente.getEventosParticipados() + 1);
	        }else {
	        	cliente.setEventosParticipados(cliente.getEventosParticipados() - 1);
	        }
	        if(cliente.getEventosParticipados()==5) {
	        	cliente.setTieneEntradaGratis(true);
	        }else{
	        	cliente.setTieneEntradaGratis(false);
	        }
	        clienteRepository.save(cliente);
	        reservaRepository.save(reserva);
	    }
	    @Override
	    public List<Reserva> buscarPorEvento(Evento evento){
	    	 List<Reserva> reserva = reservaRepository.findByEvento(evento);
	    	 return reserva;
	    }
	    @Override
	    @Transactional
	    public void eliminarReserva(Long idReserva) {
	        if (!reservaRepository.existsById(idReserva)) {
	            throw new RuntimeException("La reserva con ID " + idReserva + " no existe.");
	        }
	        Reserva reserva = reservaRepository.findById(idReserva)
	                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
	        Cliente cliente = reserva.getCliente();
	        if(reserva.getEstadoPago()==EstadoPago.PAGADO) {
	        	cliente.setEventosParticipados(cliente.getEventosParticipados() - 1);        	
	        }
	        Evento evento = reserva.getEvento();
	        evento.setReservasRealizadas(evento.getReservasRealizadas() - 1);
	        reservaRepository.deleteById(idReserva);
	    }
	    @Override
	    public List<Reserva> obtenerTodas() {
	        return reservaRepository.findAll();
	    }
	}