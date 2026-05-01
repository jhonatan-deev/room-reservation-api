package com.reservas.room.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="salas")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private int capacidade;
    @OneToMany(mappedBy = "sala",fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    public Sala() {}
    public Sala(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
