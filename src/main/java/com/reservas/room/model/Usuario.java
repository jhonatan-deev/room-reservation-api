package com.reservas.room.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String email;
    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>(); // Lista de reservas associadas ao usuário; inicializada para evitar NullPointerException
    // antes do JPA carregar os dados

    public Usuario() {}
    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
