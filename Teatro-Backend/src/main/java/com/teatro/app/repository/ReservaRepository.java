package com.teatro.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teatro.app.entity.Evento;
import com.teatro.app.entity.Reserva;
import com.teatro.app.entity.enums.EstadoPago;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	List<Reserva> findByEvento(Evento evento);
	@Query("SELECT COUNT(r) FROM Reserva r WHERE r.cliente.dni = :dni AND EXTRACT(YEAR FROM r.evento.fecha) = :anio")
	long contarReservasPorClienteEnAnio(@Param("dni") String dni, @Param("anio") int anio);
	List<Reserva> findByEstadoPagoAndEvento_Fecha(EstadoPago estadoPago,LocalDate fecha);
	List<Reserva> findByClienteDni(String dni);
}
