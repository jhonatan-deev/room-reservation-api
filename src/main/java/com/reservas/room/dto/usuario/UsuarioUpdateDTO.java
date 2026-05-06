package com.reservas.room.dto.usuario;

import com.reservas.room.enums.TipoUsuario;
import jakarta.validation.constraints.Email;

public record UsuarioUpdateDTO(
        String nome,
        @Email
        String email,
        TipoUsuario tipo
) {
}
