package com.reservas.room.dto.sala;

import java.time.LocalDateTime;

public record SalaResponseDTO(
        Long id,
        String nome,
        int capacidade,
        boolean disponivel,
        LocalDateTime tipo
) {
}
