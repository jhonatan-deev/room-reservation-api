package com.reservas.room.service;

import com.reservas.room.dto.sala.SalaRequestDTO;
import com.reservas.room.dto.sala.SalaResponseDTO;
import com.reservas.room.dto.sala.SalaUpdateDTO;
import com.reservas.room.exception.SalaNotFoundException;
import com.reservas.room.mapper.SalaMapper;
import com.reservas.room.model.Sala;
import com.reservas.room.respository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
    }

    public SalaResponseDTO createSala(SalaRequestDTO salaRequestDTO){
        Sala sala = salaMapper.toEntity(salaRequestDTO);
        sala = salaRepository.save(sala);
        return salaMapper.toDTO(sala);
    }

    public SalaResponseDTO updateSala(SalaUpdateDTO salaUpdateDTO, Long id){
        Sala sala = salaRepository.findById(id).orElseThrow(
                () -> new SalaNotFoundException("Sala inexistente"));
        salaMapper.updateEntity(salaUpdateDTO, sala);
        sala = salaRepository.save(sala);
        return salaMapper.toDTO(sala);
    }

    public void deleteSala(Long id) {
        Sala sala = salaRepository.findById(id).orElseThrow(
                () -> new SalaNotFoundException("Sala inexistente")
        );
        salaRepository.delete(sala);
    }

    public List<SalaResponseDTO> getSalas(){
        return salaRepository.findAll().stream()
                .map(salaMapper::toDTO)
                .toList();
    }

    public SalaResponseDTO getSala(Long id) {
        return salaMapper.toDTO(salaRepository.findById(id).orElseThrow(() -> new SalaNotFoundException("Sala não encontrada")));
    }


}
