package com.reservas.room.validation;

import com.reservas.room.exception.ValidationException;
import com.reservas.room.model.Reserva;
import com.reservas.room.model.Sala;
import com.reservas.room.model.Usuario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ReservaValidation {
    public void validar(Reserva reserva) {
        validarSala(reserva.getSala());
        validarDatasNaoNullas(reserva.getDataHoraInicio(), reserva.getDataHoraFim());
        validarDataInicialMaiorQueFinal(reserva.getDataHoraInicio(), reserva.getDataHoraFim());
        validarValor(reserva.getValor());
        validarUsuario(reserva.getUsuario());
    }

    private void validarSala(Sala sala) {
        if(sala == null) {
            throw new ValidationException("Sala é obrigatória");
        }
        if(!sala.isDisponivel()){
            throw new ValidationException("Sala está indisponível");
        }
        if(sala.getCapacidade() <= 0){
            throw new ValidationException("Capacidade da sala deve ser maior que zero");
        }
    }

    private void validarDatasNaoNullas(LocalDateTime inicio, LocalDateTime fim) {
        if(inicio == null || fim == null) {
            throw new ValidationException("Erro nenhuma data pode estar vazia");
        }
    }

    private void validarDataInicialMaiorQueFinal(LocalDateTime inicio, LocalDateTime fim) {
        if(inicio.isAfter(fim)) {
            throw new ValidationException("A Data inicial não pode ser maior que a final");
        }
    }

    private void validarValor(BigDecimal valor) {
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("O valor não pode ser menor que zero nem vazio.");
        }
    }

    private void validarUsuario(Usuario usuario) {
        if(usuario == null) {
            throw new ValidationException("Usuário não pode estar vazio");
        }
    }
}
