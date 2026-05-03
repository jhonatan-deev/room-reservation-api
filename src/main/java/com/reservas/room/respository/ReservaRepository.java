package com.reservas.room.respository;

import com.reservas.room.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    //Método pra buscar conflitos de horário + Ignorar reservas CANCELADAS
    @Query("SELECT r FROM Reserva r WHERE r.sala.id = :salaId AND r.statusReserva <> 'CANCELADA' AND r.dataHoraInicio <:fim AND r.dataHoraFim> :inicio")
    List<Reserva> buscarConflitos(@Param("salaId") Long salaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
