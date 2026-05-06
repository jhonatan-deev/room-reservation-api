package com.reservas.room.dto.reserva;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaRequestDTO(
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        BigDecimal valor,

        @NotNull(message = "Data de início é obrigatória")
        @FutureOrPresent(message = "Data de início deve ser presente ou futura")
        LocalDateTime dataHoraInicio,

        @NotNull(message = "Data de fim é obrigatória")
        @Future(message = "Data de fim deve ser futura")
        LocalDateTime dataHoraFim,

        @NotNull(message = "Usuário é obrigatório")
        Long usuarioId,

        @NotNull(message = "Sala é obrigatória")
        Long salaId
) {
}
