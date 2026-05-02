package com.reservas.room.model;

import com.reservas.room.enums.StatusReserva;
import com.reservas.room.exception.DataCannotBeNull;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private LocalDateTime dataHoraInicio; // Datas do período da reserva (definidas pelo usuário)
    @Column(nullable = false)
    private LocalDateTime dataHoraFim;
    private LocalDateTime dataCancelamento;
    // Data de criação (preenchida automaticamente pelo sistema)
    @Column(updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    private Sala sala;

    public Reserva() {}

    public Reserva(BigDecimal valor,
                   LocalDateTime dataHoraInicio,
                   LocalDateTime dataHoraFim,
                   Usuario usuario,
                   Sala sala) {

        validarDados(sala, valor, usuario, dataHoraInicio, dataHoraFim);

        this.valor = valor;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.usuario = usuario;
        this.sala = sala;
    }

    private void validarDados(Sala sala, BigDecimal valor, Usuario usuario, LocalDateTime inicio, LocalDateTime fim) {

        if (sala == null) {
            throw new DataCannotBeNull("Sala é obrigatória");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DataCannotBeNull("Valor deve ser positivo");
        }

        if (usuario == null) {
            throw new DataCannotBeNull("Usuário é obrigatório");
        }

        if (inicio == null || fim == null) {
            throw new DataCannotBeNull("Datas são obrigatórias");
        }

        if (inicio.isAfter(fim)) {
            throw new DataCannotBeNull("Data de início não pode ser depois da data de fim");
        }
    }

    @PrePersist
    private void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.statusReserva = StatusReserva.PENDENTE;
    }

    public Long getId() {
        return id;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


}
