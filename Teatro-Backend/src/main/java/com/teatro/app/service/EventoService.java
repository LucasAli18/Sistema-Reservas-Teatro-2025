package com.teatro.app.service;

import java.util.List;
import java.util.Optional;
import com.teatro.app.entity.Evento;

public interface EventoService {
    Evento crearEvento(Evento evento);
    Evento editarEvento(Long id, Evento eventoActualizado);
    void eliminarEvento(Long id);
    List<Evento> listarEventos();
    Optional<Evento> buscarPorId(Long id);
}

