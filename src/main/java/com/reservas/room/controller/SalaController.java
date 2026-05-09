package com.reservas.room.controller;

import com.reservas.room.dto.sala.SalaRequestDTO;
import com.reservas.room.dto.sala.SalaResponseDTO;
import com.reservas.room.dto.sala.SalaUpdateDTO;
import com.reservas.room.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> createRoom(@RequestBody @Valid SalaRequestDTO salaRequestDTO){
        SalaResponseDTO sala = salaService.createRoom(salaRequestDTO);
        return ResponseEntity.ok().body(sala);
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> getAllRooms(){
        List<SalaResponseDTO> salas = salaService.getAllRooms();
        return ResponseEntity.ok().body(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> getRoom(@PathVariable Long id){
        SalaResponseDTO sala = salaService.getRoomById(id);
        return ResponseEntity.ok().body(sala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> putRoom(@PathVariable Long id, @RequestBody @Valid SalaUpdateDTO salaUpdateDTO){
        SalaResponseDTO salaAtualizada = salaService.updateRoom(salaUpdateDTO, id);
        return ResponseEntity.ok().body(salaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id){
        salaService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
