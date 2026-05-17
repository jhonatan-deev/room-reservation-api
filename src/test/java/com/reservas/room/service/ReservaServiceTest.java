package com.reservas.room.service;

import com.reservas.room.dto.reserva.ReservaRequestDTO;
import com.reservas.room.dto.reserva.ReservaResponseDTO;
import com.reservas.room.enums.StatusReserva;
import com.reservas.room.exception.ReservationNotFoundException;
import com.reservas.room.mapper.ReservaMapper;
import com.reservas.room.model.Reserva;
import com.reservas.room.model.Sala;
import com.reservas.room.model.Usuario;
import com.reservas.room.respository.ReservaRepository;
import com.reservas.room.validation.ReservaValidation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {
    @InjectMocks
    private ReservaService reservaService;
    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private ReservaMapper reservaMapper;
    @Mock
    private ReservaValidation validator;
    @Mock
    private ConflitoReservaService conflitoService;

    @Test
    void deveCriarERetornarReservaResponseDTOQuandoDadosForemValidos(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setId(1L);
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("JHONATAN");

        ReservaRequestDTO reserva = new ReservaRequestDTO(new BigDecimal("150.50"),LocalDateTime.of(2026, 5, 29, 10, 0),
                LocalDateTime.of(2026, 5, 30, 14, 30),usuario.getId(),sala.getId());

        ReservaResponseDTO responseDTO = new ReservaResponseDTO(1L,
                StatusReserva.PENDENTE,
                new BigDecimal("150.50"),
                LocalDateTime.of(2026, 5, 29, 10, 0),
                LocalDateTime.of(2026, 5, 30, 14, 30),
                null,
                LocalDateTime.now(),
                1L,
                1L           );

        Reserva reservaSemId = new Reserva();
        Reserva reservaSalvaComID = new Reserva();
        reservaSalvaComID.setId(1L);

        BDDMockito.given(reservaMapper.toEntity(reserva)).willReturn(reservaSemId);
        BDDMockito.given(reservaRepository.save(reservaSemId)).willReturn(reservaSalvaComID);
        BDDMockito.given(reservaMapper.toDTO(reservaSalvaComID)).willReturn(responseDTO);
        // ACT
        ReservaResponseDTO resultado = reservaService.createReserve(reserva);
        Assertions.assertEquals(1L, resultado.id());
        Assertions.assertEquals(StatusReserva.PENDENTE, resultado.status());
        Assertions.assertEquals(new BigDecimal("150.50"), resultado.valor());
        Assertions.assertNull(resultado.dataCancelamento());
        Assertions.assertEquals(1L, resultado.usuarioId());
        Assertions.assertEquals(1L, resultado.salaId());
        //ASSERT
        BDDMockito.then(reservaRepository).should().save(any(Reserva.class));
        BDDMockito.then(validator).should().validar(any(Reserva.class));
        BDDMockito.then(conflitoService).should().validarConflito(any(Reserva.class));
    }

    @Test
    void deveCancelarReservaERetornarStatusCancelada(){
        //ARRANGE
        Reserva reservaExistente = new Reserva();
        reservaExistente.setId(1L);
        reservaExistente.setStatusReserva(StatusReserva.ATIVA);

        Reserva reservaCancelada = new Reserva();
        reservaCancelada.setStatusReserva(StatusReserva.CANCELADA);
        reservaCancelada.setDataCancelamento(LocalDateTime.now());

        ReservaResponseDTO responseDTO = new ReservaResponseDTO(1L,
                StatusReserva.CANCELADA,
                new BigDecimal("150.50"),
                LocalDateTime.of(2026, 5, 29, 10, 0),
                LocalDateTime.of(2026, 5, 30, 14, 30),
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L,
                1L           );

        BDDMockito.given(reservaRepository.findById(1L)).willReturn(Optional.of(reservaExistente));
        BDDMockito.given(reservaRepository.save(reservaExistente)).willReturn(reservaCancelada);
        BDDMockito.given(reservaMapper.toDTO(reservaCancelada)).willReturn(responseDTO);

        //ACT
        ReservaResponseDTO resposta = reservaService.cancelReserve(1L);

        //ASSERT
        Assertions.assertEquals(StatusReserva.CANCELADA, resposta.status());
        Assertions.assertNotNull(resposta.dataCancelamento());
        BDDMockito.then(reservaRepository).should().save(any(Reserva.class));

    }

    @Test
    void deveLancarExceptionQuandoCancelarReservaComIdInexistente(){
        //ARRANGE
        Long reservaId = 99L;
        BDDMockito.given(reservaRepository.findById(99L)).willReturn(Optional.empty());
        //ACT + ASSERT
        ReservationNotFoundException exception = assertThrows(ReservationNotFoundException.class, () -> reservaService.cancelReserve(99L));
        Assertions.assertEquals("Reservation not found with id: " + 99L,exception.getMessage());
        BDDMockito.then(reservaRepository).should(never()).save(any(Reserva.class));

    }

    @Test
    void deveBuscarReservaPorIdComSucesso(){
        //ARRANGE
        Long idDeBusca= 1L;

        Reserva reservaExistente = new Reserva();
        reservaExistente.setId(1L);

        ReservaResponseDTO responseDTO = new ReservaResponseDTO(1L,
                StatusReserva.PENDENTE,
                new BigDecimal("150.50"),
                LocalDateTime.of(2026, 5, 29, 10, 0),
                LocalDateTime.of(2026, 5, 30, 14, 30),
                null,
                LocalDateTime.now(),
                1L,
                1L           );

        BDDMockito.given(reservaRepository.findById(1L)).willReturn(Optional.of(reservaExistente));
        BDDMockito.given(reservaMapper.toDTO(reservaExistente)).willReturn(responseDTO);
        //ACT
        ReservaResponseDTO resultado = reservaService.getReserve(idDeBusca);
        //ASSERT
        Assertions.assertEquals(1L, resultado.id());
        Assertions.assertEquals(StatusReserva.PENDENTE, resultado.status());
        Assertions.assertEquals(new BigDecimal("150.50"), resultado.valor());
        Assertions.assertNull(resultado.dataCancelamento());
        Assertions.assertEquals(1L, resultado.usuarioId());
        Assertions.assertEquals(1L, resultado.salaId());
        BDDMockito.then(reservaRepository).should().findById(anyLong());
    }

    @Test
    void deveLancarReservationNotFoundExceptionQuandoIdNaoExistir(){
        Long idQueNaoExiste = 1L;
        BDDMockito.given(reservaRepository.findById(1L)).willReturn(Optional.empty());
        ReservationNotFoundException exception = assertThrows(ReservationNotFoundException.class, () -> reservaService.getReserve(1L));
        Assertions.assertEquals("Reservation not found with id: " + 1L,exception.getMessage());
    }


}
