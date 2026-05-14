package com.reservas.room.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private boolean disponivel = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Version
    private Long version;

    // Lista de reservas associadas a sala; inicializada para evitar NullPointerException
    // antes do JPA carregar os dados
    @OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public Sala() {}

    public Sala(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.disponivel = true;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public List<Reserva> getReservas() {
        return Collections.unmodifiableList(reservas);
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void setId(long l) {
        this.id = l;
    }
}