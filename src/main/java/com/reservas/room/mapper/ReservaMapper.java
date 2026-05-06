package com.reservas.room.mapper;

import com.reservas.room.dto.reserva.ReservaRequestDTO;
import com.reservas.room.dto.reserva.ReservaResponseDTO;
import com.reservas.room.dto.reserva.ReservaUpdateDTO;
import com.reservas.room.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    public Reserva toEntity(ReservaRequestDTO dto){
        Reserva reserva = new Reserva();
        reserva.setValor(dto.valor());
        reserva.setDataHoraInicio(dto.dataHoraInicio());
        reserva.setDataHoraFim(dto.dataHoraFim());
        return reserva;
    }

    public ReservaResponseDTO toDTO(Reserva reserva){
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getStatusReserva(),
                reserva.getValor(),
                reserva.getDataHoraInicio(),
                reserva.getDataHoraFim(),
                reserva.getDataCancelamento(),
                reserva.getDataCriacao(),
                reserva.getUsuario().getId(),
                reserva.getSala().getId()
        );
    }

    public void reservaUpdateDTO(ReservaUpdateDTO dto, Reserva reserva){
        if (dto.valor() != null) {
            reserva.setValor(dto.valor());
        }
        if (dto.dataHoraInicio() != null) {
            reserva.setDataHoraInicio(dto.dataHoraInicio());
        }
        if (dto.dataHoraFim() != null) {
            reserva.setDataHoraFim(dto.dataHoraFim());
        }
    }
}
