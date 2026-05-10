package com.reservas.room.service;

import com.reservas.room.dto.usuario.UsuarioRequestDTO;
import com.reservas.room.dto.usuario.UsuarioResponseDTO;
import com.reservas.room.dto.usuario.UsuarioUpdateDTO;
import com.reservas.room.exception.UserNotFoundException;
import com.reservas.room.mapper.UsuarioMapper;
import com.reservas.room.model.Usuario;
import com.reservas.room.respository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }
    @Transactional
    public UsuarioResponseDTO createUser(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }
    @Transactional
    public UsuarioResponseDTO updateUser(UsuarioUpdateDTO usuarioUpdateDTO, Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        usuarioMapper.updateEntity(usuarioUpdateDTO, usuario);
        usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }
    @Transactional
    public void deleteUserById(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDTO getUserById(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        return usuarioMapper.toDTO(usuario);
    }

    public List<UsuarioResponseDTO> getAllUsers(){
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }


}
