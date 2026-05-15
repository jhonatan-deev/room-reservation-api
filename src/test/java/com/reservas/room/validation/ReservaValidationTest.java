package com.reservas.room.validation;

import com.reservas.room.model.Reserva;
import com.reservas.room.exception.ValidationException;
import com.reservas.room.model.Sala;
import com.reservas.room.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ReservaValidationTest {
    @InjectMocks
    ReservaValidation validation;

    @Test
    void deveLancarValidationExceptionQuandoSalaDaReservaForNula() {
        //ARRANGE
        Reserva reserva = new Reserva();
        //ACT + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("Sala é obrigatória", exception.getMessage());
    }

    @Test
    void deveLancarValidationExceptionQuandoSalaDaReservaEstiverIndisponivel(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setDisponivel(false);
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setValor(new BigDecimal("150.50"));
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        reserva.setUsuario(usuario);
        //ACT + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("Sala está indisponível", exception.getMessage());

    }

    @Test
    void deveLancarValidationExceptionQuandoCapacidadeDaSalaForMenorOuIgualAZero(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 0);
        sala.setDisponivel(true);
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setValor(new BigDecimal("150.50"));
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        //ACT + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("Capacidade da sala deve ser maior que zero",  exception.getMessage());
    }
    @Test
    void deveLancarValidationExceptionQuandoDatasDaReservaNaoForemInformadas(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setDisponivel(true);
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setValor(new BigDecimal("150.50"));
        //ACT + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("Erro nenhuma data pode estar vazia",  exception.getMessage());
    }

    @Test
    void deveLancarValidationExceptionQuandoDataInicialForMaiorQueDataFinal(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setDisponivel(true);
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setValor(new BigDecimal("150.50"));
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 30, 14, 30));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 29, 10, 0));
        //ACT + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("A Data inicial não pode ser maior que a final",  exception.getMessage());
    }
    @Test
    void deveLancarValidationExceptionQuandoValorDaReservaForNulo(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setDisponivel(true);
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        // Act + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("O valor não pode ser menor que zero nem vazio.",  exception.getMessage());
    }

    @Test
    void deveLancarValidationExceptionQuandoValorDaReservaForMenorOuIgualAZero(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setDisponivel(true);
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        Reserva reserva = new Reserva();
        reserva.setValor(BigDecimal.ZERO);
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        // Act + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("O valor não pode ser menor que zero nem vazio.",  exception.getMessage());
    }

    @Test
    void deveLancarValidationExceptionQuandoUsuarioDaReservaForNulo(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setDisponivel(true);
        Reserva reserva = new Reserva();
        reserva.setValor(new BigDecimal("150.50"));
        reserva.setSala(sala);
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        // Act + ASSERT
        ValidationException exception = assertThrows(ValidationException.class, () -> validation.validar(reserva));
        Assertions.assertEquals("Usuário não pode estar vazio",  exception.getMessage());
    }

    @Test
    void deveValidarReservaComSucessoQuandoTodosOsDadosForemValidos(){
        //ARRANGE
        Sala sala = new Sala("Sala A", 2);
        sala.setDisponivel(true);
        Usuario usuario = new Usuario();
        usuario.setNome("JHONATAN");
        Reserva reserva = new Reserva();
        reserva.setValor(new BigDecimal("150.50"));
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setDataHoraInicio(LocalDateTime.of(2026, 5, 29, 10, 0));
        reserva.setDataHoraFim(LocalDateTime.of(2026, 5, 30, 14, 30));
        // Act + ASSERT
        Assertions.assertDoesNotThrow(() -> validation.validar(reserva));
    }
}