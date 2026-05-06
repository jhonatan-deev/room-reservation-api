package com.reservas.room.dto.usuario;

import com.reservas.room.enums.TipoUsuario;
import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoUsuario tipo,
        LocalDateTime dataCriacao
) {
}
