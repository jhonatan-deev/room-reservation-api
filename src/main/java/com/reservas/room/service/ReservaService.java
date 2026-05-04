package com.reservas.room.service;

import com.reservas.room.enums.StatusReserva;
import com.reservas.room.exception.ReservationNotFoundException;
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

    public ReservaService(ReservaValidation reservaValidation, ReservaRepository reservaRepository, ConflitoReservaService conflitoReservaService) {
        this.reservaValidation = reservaValidation;
        this.reservaRepository = reservaRepository;
        this.conflitoReservaService = conflitoReservaService;
    }

    public Reserva createReserva(Reserva reserva){
        reservaValidation.validar(reserva);
        conflitoReservaService.validarConflito(reserva);
        Reserva novaReserva = reservaRepository.save(reserva);
        return novaReserva;
    }

    public void cancelReserva(Long reservaId){
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservaId));
        reserva.setStatusReserva(StatusReserva.CANCELADA);
        reserva.setDataCancelamento(LocalDateTime.now());
        reservaRepository.save(reserva);
    }

    public Reserva buscarReserva(Long reservaId){
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservaId));
        return reserva;
    }

}

