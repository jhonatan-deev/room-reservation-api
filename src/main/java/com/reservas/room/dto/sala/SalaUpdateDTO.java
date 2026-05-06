package com.reservas.room.dto.sala;

public record SalaUpdateDTO(
        String nome,
        Integer capacidade,
        Boolean disponivel
) {
}