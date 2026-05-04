package com.reservas.room.service;

import com.reservas.room.exception.ConflitoReservaException;
import com.reservas.room.model.Reserva;
import com.reservas.room.model.Sala;
import com.reservas.room.respository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConflitoReservaService {
    private final ReservaRepository reservaRepository;
    public ConflitoReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void validarConflito(Reserva reserva) {
        Long salaId = reserva.getSala().getId();
        LocalDateTime dataInicial = reserva.getDataHoraInicio();
        LocalDateTime dataHoraFim= reserva.getDataHoraFim();

        List<Reserva> conflitos = reservaRepository.buscarConflitos(salaId, dataInicial, dataHoraFim);
        if (!conflitos.isEmpty()) {
            throw new ConflitoReservaException("Sala já reservada nesse horário");
        }
    }


}
