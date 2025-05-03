package com.teatro.app.dto;

import com.teatro.app.entity.enums.EstadoPago;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {
    @NotBlank(message = "El DNI del cliente es obligatorio")
    private String dniCliente;

    @NotNull(message = "Debe indicar el evento")
    private Long idEvento;

    @NotBlank(message = "Debe seleccionar un tipo de entrada")
    private String tipoEntrada;

    @NotNull(message = "Debe especificar el estado de pago")
    private EstadoPago estadoPago;
}
