package com.teatro.app.controller.impl;

import com.teatro.app.controller.IReservaController;
import com.teatro.app.dto.ReservaDTO;
import com.teatro.app.entity.Reserva;
import com.teatro.app.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaControllerImpl implements IReservaController {

    private final ReservaService reservaService;

    public ReservaControllerImpl(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Override
    public Reserva crearReserva(@RequestBody ReservaDTO dto) {
        return reservaService.crearReserva(dto);
    }

    @Override
    public ResponseEntity<String> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.ok("Reserva eliminada correctamente.");
    }

    @Override
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada) {
        Reserva reserva = reservaService.editarReserva(
            id,
            reservaActualizada.getEstadoPago(),
            reservaActualizada.getTipoEntrada()
        );
        return ResponseEntity.ok(reserva);
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservaService.obtenerTodas();
    }
}
