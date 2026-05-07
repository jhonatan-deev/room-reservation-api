package com.reservas.room.service;

import com.reservas.room.dto.reserva.ReservaRequestDTO;
import com.reservas.room.dto.reserva.ReservaResponseDTO;
import com.reservas.room.enums.StatusReserva;
import com.reservas.room.exception.ReservationNotFoundException;
import com.reservas.room.mapper.ReservaMapper;
import com.reservas.room.model.Reserva;
import com.reservas.room.respository.ReservaRepository;
import com.reservas.room.validation.ReservaValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservaService {
    private final ReservaValidation reservaValidation;
    private final ReservaRepository reservaRepository;
    private final ConflitoReservaService conflitoReservaService;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaValidation reservaValidation, ReservaRepository reservaRepository, ConflitoReservaService conflitoReservaService, ReservaMapper reservaMapper) {
        this.reservaValidation = reservaValidation;
        this.reservaRepository = reservaRepository;
        this.conflitoReservaService = conflitoReservaService;
        this.reservaMapper = reservaMapper;
    }

    public ReservaResponseDTO createReserva(ReservaRequestDTO reservaRequestDTO){
        Reserva reserva = reservaMapper.toEntity(reservaRequestDTO);
        reservaValidation.validar(reserva);
        conflitoReservaService.validarConflito(reserva);
        reserva = reservaRepository.save(reserva);
        return reservaMapper.toDTO(reserva);
    }

    public ReservaResponseDTO cancelReserva(Long id){
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
        reserva.setStatusReserva(StatusReserva.CANCELADA);
        reserva.setDataCancelamento(LocalDateTime.now());
        reserva = reservaRepository.save(reserva);
        return reservaMapper.toDTO(reserva);
    }

    public ReservaResponseDTO buscarReserva(Long id){
        return reservaMapper.toDTO(
                reservaRepository.findById(id) .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id))
        );
    }

}

