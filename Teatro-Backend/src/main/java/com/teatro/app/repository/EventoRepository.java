package com.teatro.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teatro.app.entity.Evento;
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
