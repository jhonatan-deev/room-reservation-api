package com.reservas.room.dto.reserva;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaUpdateDTO(
        BigDecimal valor,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        Long usuarioId,
        Long salaId
) {
}
