package com.reservas.room.service;

import com.reservas.room.dto.sala.SalaRequestDTO;
import com.reservas.room.dto.sala.SalaResponseDTO;
import com.reservas.room.dto.sala.SalaUpdateDTO;
import com.reservas.room.exception.SalaNotFoundException;
import com.reservas.room.mapper.SalaMapper;
import com.reservas.room.model.Sala;
import com.reservas.room.respository.SalaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {
    @Mock
    private SalaRepository salaRepository;
    @InjectMocks
    private SalaService salaService;
    @Mock
    private SalaMapper salaMapper;

    @Test
    void deveCriarSalaComSucesso() {
        //ARRANGE
        SalaRequestDTO dto = new SalaRequestDTO("Sala A", 2);
        Sala salaSalva = new Sala("Sala A", 2);
        salaSalva.setId(1L);
        SalaResponseDTO responseDTO = new SalaResponseDTO(1L, "Sala A", 2
        , true, LocalDateTime.now());
        BDDMockito.given(salaMapper.toEntity(dto)).willReturn(salaSalva);
        BDDMockito.given(salaRepository.save(salaSalva)).willReturn(salaSalva);
        BDDMockito.given(salaMapper.toDTO(salaSalva)).willReturn(responseDTO);
        //ACT
        SalaResponseDTO resultado = salaService.createRoom(dto);
        Assertions.assertEquals(resultado.nome(), "Sala A");
        Assertions.assertEquals(resultado.capacidade(), 2);
        Assertions.assertEquals(resultado.id(), 1L);
        //ASSERT
        BDDMockito.then(salaRepository).should().save(any(Sala.class));
    }
    @Test
    void deveBuscarSalaPorIdExistente(){
        //ARRANGE
        Sala salaSalva = new Sala("Sala A", 2);
        salaSalva.setId(1L);
        SalaResponseDTO dto =  new SalaResponseDTO(1L, "Sala A", 2
                , true, LocalDateTime.now());
        BDDMockito.given(salaRepository.findById(1L)).willReturn(Optional.of(salaSalva));
        BDDMockito.given(salaMapper.toDTO(salaSalva)).willReturn(dto);
        //ACT
        SalaResponseDTO salaBuscada = salaService.getRoomById(1L);
        Assertions.assertEquals(salaBuscada.nome(), "Sala A");
        Assertions.assertEquals(salaBuscada.capacidade(), 2);
        Assertions.assertEquals(salaBuscada.id(), 1L);
        //ASSERT
        BDDMockito.then(salaRepository).should().findById(1L);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremSalas(){
        //ARRANGE
        BDDMockito.given(salaRepository.findAll()).willReturn(Collections.emptyList());
        //ACT
        List<SalaResponseDTO> listaVazia = salaService.getAllRooms();
        //ASSERT
        Assertions.assertEquals(listaVazia.size(), 0);
        BDDMockito.then(salaRepository).should().findAll();
    }
    @Test
    void deveRetornarListaDeSalasQuandoExistiremRegistros() {
        //ARRANGE
        Sala sala1 = new Sala("Sala A", 2);
        sala1.setId(1L);
        Sala sala2 = new Sala("Sala B", 2);
        sala2.setId(2L);
        BDDMockito.given(salaRepository.findAll()).willReturn(Arrays.asList(sala1, sala2));
        SalaResponseDTO respostaSal1 = new SalaResponseDTO(1L, "Sala A", 2 ,true, LocalDateTime.now());
        SalaResponseDTO respostaSal2 = new SalaResponseDTO(2L, "Sala B", 2 ,true, LocalDateTime.now());
        BDDMockito.given(salaMapper.toDTO(sala1)).willReturn(respostaSal1);
        BDDMockito.given(salaMapper.toDTO(sala2)).willReturn(respostaSal2);
        //ACT
        List<SalaResponseDTO> listarSalas = salaService.getAllRooms();
        //ASSERT
        Assertions.assertEquals(listarSalas.size(), 2);
        Assertions.assertEquals(listarSalas.get(0).nome(), "Sala A");
        Assertions.assertEquals(listarSalas.get(1).nome(), "Sala B");
    }

    @Test
    void deveAtualizarSalaPorIdComSucesso() {

        //ARRANGE
        Long idSala = 1L;
        SalaUpdateDTO updateDTO = new SalaUpdateDTO("Sala B", 4, true);

        Sala salaExistente = new Sala("Sala A", 2);
        salaExistente.setId(1L);


        Sala salaAtualizada = new Sala("Sala B", 4);
        salaAtualizada.setId(1L);

        SalaResponseDTO responseDTO = new SalaResponseDTO(1L, "Sala B", 4, true, LocalDateTime.now());


        BDDMockito.given(salaRepository.findById(1L)).willReturn(Optional.of(salaExistente));
        BDDMockito.given(salaRepository.save(any(Sala.class))).willReturn(salaAtualizada);
        BDDMockito.given(salaMapper.toDTO(salaAtualizada)).willReturn(responseDTO);

        //ACT
        SalaResponseDTO resultado = salaService.updateRoom(updateDTO, idSala);

        //ASSERT
        Assertions.assertEquals("Sala B", resultado.nome());
        Assertions.assertEquals(4, resultado.capacidade());
        Assertions.assertEquals(1L, resultado.id());

        BDDMockito.then(salaRepository).should().findById(1L);
        BDDMockito.then(salaMapper).should().updateEntity(updateDTO, salaExistente);
        BDDMockito.then(salaRepository).should().save(any(Sala.class));
    }

    @Test
    void deveLancarSalaNotFoundExceptionQuandoAtualizarSalaComIdInexistente(){
        //ARRANGE
        Long idInexistente  = 1L;
        SalaUpdateDTO updateDTO = new SalaUpdateDTO("Sala B", 4, true);
        BDDMockito.given(salaRepository.findById(idInexistente)).willReturn(Optional.empty());
        //ACT + ASSERT
        SalaNotFoundException exception = assertThrows(
                SalaNotFoundException.class,
                () -> salaService.updateRoom(updateDTO, idInexistente)
        );
        BDDMockito.then(salaRepository).should(never()).save(any(Sala.class));
        Assertions.assertEquals("Sala inexistente", exception.getMessage());
    }

    @Test
    void deveLancarSalaNotFoundExceptionAoTentarDeletarSalaComIdInexistente(){
        //ARRANGE
        Long idInexistente  = 1L;
        BDDMockito.given(salaRepository.findById(idInexistente)).willReturn(Optional.empty());
        //ACT + ASSERT
        SalaNotFoundException exception = assertThrows(
                SalaNotFoundException.class,
                () -> salaService.deleteRoom(idInexistente)
        );
        Assertions.assertEquals("Sala inexistente", exception.getMessage());
        BDDMockito.then(salaRepository).should(never()).delete(any(Sala.class));
    }

    @Test
    void deveDeletarSalaComSucesso(){
        //ARRANGE
        Long idExistente  = 1L;
        Sala salaExistente = new Sala("Sala A", 2);
        BDDMockito.given(salaRepository.findById(idExistente)).willReturn(Optional.of(salaExistente));
        //ACT
        Assertions.assertDoesNotThrow(() -> salaService.deleteRoom(idExistente));
        //ASSERT
        BDDMockito.then(salaRepository).should().delete(any(Sala.class));

    }


    @Test
    void deveLancarSalaNotFoundExceptionQuandoBuscarSalaPorIdInexistente(){
        //ARRANGE
        Long idExistente  = 1L;
        BDDMockito.given(salaRepository.findById(idExistente)).willReturn(Optional.empty());
        //ACT + ASSERT
        SalaNotFoundException exception = assertThrows(SalaNotFoundException.class, () -> salaService.getRoomById(idExistente));
        Assertions.assertEquals("Sala não encontrada", exception.getMessage());
    }
}

