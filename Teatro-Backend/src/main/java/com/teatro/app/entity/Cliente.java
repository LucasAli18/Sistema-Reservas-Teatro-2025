package com.teatro.app.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cliente")
@Builder
@Entity
public class Cliente {
    @Id
    @NotBlank(message = "El DNI no puede estar vacío")
    private String dni;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Email(message = "El email no es válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;
    private int eventosParticipados;
    private boolean tieneEntradaGratis;
}
