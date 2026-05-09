package com.reservas.room.controller;

import com.reservas.room.dto.reserva.ReservaRequestDTO;
import com.reservas.room.dto.reserva.ReservaResponseDTO;
import com.reservas.room.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> createReserve(@RequestBody @Valid ReservaRequestDTO reservaRequestDTO){
        ReservaResponseDTO reserva = reservaService.createReserve(reservaRequestDTO);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> getReserve(@PathVariable Long id){
        ReservaResponseDTO reserva = reservaService.getReserve(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelReserve(@PathVariable Long id){
       ReservaResponseDTO reservaCancelada = reservaService.cancelReserve(id);
       return ResponseEntity.ok(reservaCancelada);
    }

}
