package com.teatro.app.entity;

import java.time.LocalDate;

import com.teatro.app.entity.enums.EstadoPago;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reserva")
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_dni")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    private LocalDate fechaReserva;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    private String tipoEntrada; // elegimos String para mantener compatibilidad con varios enums
}
