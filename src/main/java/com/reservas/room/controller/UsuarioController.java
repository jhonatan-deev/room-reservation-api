package com.reservas.room.controller;

import com.reservas.room.dto.usuario.UsuarioRequestDTO;
import com.reservas.room.dto.usuario.UsuarioResponseDTO;
import com.reservas.room.dto.usuario.UsuarioUpdateDTO;
import com.reservas.room.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUser(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuario = usuarioService.createUser(usuarioRequestDTO);
        return ResponseEntity.ok().body(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUser(@PathVariable Long id){
        UsuarioResponseDTO usuario = usuarioService.getUserById(id);
        return ResponseEntity.ok().body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsers(){
        List<UsuarioResponseDTO> usuarios = usuarioService.getAllUsers();
        return ResponseEntity.ok().body(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO){
        UsuarioResponseDTO usuarioParaAtualizar = usuarioService.updateUser(usuarioUpdateDTO, id);
        return ResponseEntity.ok().body(usuarioParaAtualizar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        usuarioService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
