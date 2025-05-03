package com.teatro.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.teatro.app.entity.enums.TipoEvento;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;
    private int capacidadMaxima;
    private int reservasRealizadas;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;
}

