package com.reservas.room.service;

import com.reservas.room.exception.ConflitoReservaException;
import com.reservas.room.model.Reserva;
import com.reservas.room.model.Sala;
import com.reservas.room.model.Usuario;
import com.reservas.room.respository.ReservaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class ConflitoReservaServiceTest {

    @InjectMocks
    private ConflitoReservaService conflitoReservaService;
    @Mock
    private ReservaRepository reservaRepository;


    @Test
    void deveNaoLancarExcecaoQuandoNaoExistirConflitoDeReserva(){
        Sala sala = new Sala("Sala A", 2);
        sala.setId(1L);
        Reserva reserva = new Reserva();
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        reserva.setValor(new BigDecimal("150.50"));
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        BDDMockito.given(
                reservaRepository.buscarConflitos(
                        reserva.getSala().getId(),
                        reserva.getDataHoraInicio(),
                        reserva.getDataHoraFim()
                )
        ).willReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(()-> conflitoReservaService.validarConflito(reserva));

    }

    @Test
    void deveLancarConflitoReservaExceptionQuandoSalaJaEstiverReservada(){
        Sala sala = new Sala("Sala A", 2);
        sala.setId(1L);
        Reserva reserva = new Reserva();
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        reserva.setValor(new BigDecimal("150.50"));
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        BDDMockito.given(
                reservaRepository.buscarConflitos(
                        reserva.getSala().getId(),
                        reserva.getDataHoraInicio(),
                        reserva.getDataHoraFim()
                )
        ).willReturn(List.of(reserva));

        ConflitoReservaException exception = assertThrows(ConflitoReservaException.class, () -> conflitoReservaService.validarConflito(reserva));
        Assertions.assertEquals("Sala já reservada nesse horário", exception.getMessage());

    }

//ARRANGE:
//1. Mesma configuração (sala, usuario, reserva com datas)
//2. Mock do buscarConflitos retorna lista COM 1 elemento (a própria reserva ou outra)
//
//ACT + ASSERT:
//3. Espera ConflitoReservaException
//4. Verifica a mensagem: "Sala já reservada nesse horário"
}