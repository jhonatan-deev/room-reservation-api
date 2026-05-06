package com.reservas.room.mapper;

import com.reservas.room.dto.usuario.UsuarioRequestDTO;
import com.reservas.room.dto.usuario.UsuarioResponseDTO;
import com.reservas.room.dto.usuario.UsuarioUpdateDTO;
import com.reservas.room.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toEntity(UsuarioRequestDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setTipo(dto.tipo());
        return usuario;
    }

    public UsuarioResponseDTO toDTO(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo(),
                usuario.getDataCriacao()
        );
    }

    public void updateEntity(Usuario usuario, UsuarioUpdateDTO dto){
        if(dto.nome() != null){
            usuario.setNome(dto.nome());
        }
        if(dto.email() != null){
            usuario.setEmail(dto.email());
        }

        if(dto.tipo() != null){
            usuario.setTipo(dto.tipo());
        }
    }
}
