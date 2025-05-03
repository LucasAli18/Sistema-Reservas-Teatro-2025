package com.teatro.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import com.teatro.app.entity.Cliente;
import com.teatro.app.repository.ClienteRepository;
import com.teatro.app.repository.EventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock private ClienteRepository clienteRepo;
    @Mock private ReservaService reservaService;
    @Mock private EventoRepository eventoRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deberia dar correcto, si el cliente se guarda y se encuentra")
    void guardarYBuscarCliente() {
    	Cliente cliente = new Cliente("123", "Ana", "ana@email.com", 0, false);

        when(clienteRepo.save(any())).thenReturn(cliente);
        clienteService.crearCliente(cliente);

        when(clienteRepo.findById("123")).thenReturn(Optional.of(cliente));
        Optional<Cliente> resultado = clienteService.buscarPorDni("123");
        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }
}
