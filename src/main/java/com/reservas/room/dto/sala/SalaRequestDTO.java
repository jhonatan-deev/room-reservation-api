package com.reservas.room.dto.sala;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Capacidade é obrigatória")
        @Min(value = 1, message = "Capacidade deve ser positiva")
        int capacidade
) {
}
