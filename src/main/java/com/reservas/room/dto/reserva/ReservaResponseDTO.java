package com.reservas.room.dto.reserva;

import com.reservas.room.enums.StatusReserva;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        StatusReserva status,
        BigDecimal valor,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        LocalDateTime dataCancelamento,
        LocalDateTime dataCriacao,
        Long usuarioId,
        Long salaId) {
}
