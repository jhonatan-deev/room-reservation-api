package com.reservas.room.mapper;

import com.reservas.room.dto.sala.SalaRequestDTO;
import com.reservas.room.dto.sala.SalaResponseDTO;
import com.reservas.room.dto.sala.SalaUpdateDTO;
import com.reservas.room.model.Sala;
import org.springframework.stereotype.Component;

@Component
public class SalaMapper {
    public Sala toEntity(SalaRequestDTO dto){
        Sala sala = new Sala();
        sala.setNome(dto.nome());
        sala.setCapacidade(dto.capacidade());
        return sala;
    }

    public SalaResponseDTO toDTO(Sala sala){
        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.isDisponivel(),
                sala.getDataCriacao()

        );
    }

    public void updateEntity(SalaUpdateDTO dto, Sala sala) {
        if (dto.nome() != null) {
            sala.setNome(dto.nome());
        }
        if (dto.capacidade() != null) {
            sala.setCapacidade(dto.capacidade());
        }
        if (dto.disponivel() != null) {
            sala.setDisponivel(dto.disponivel());
        }
    }
}
