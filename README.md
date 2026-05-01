# 🏢 Room Reservation API

API para gerenciamento de reservas de salas desenvolvida com **Java + Spring Boot**.

Este projeto tem como objetivo praticar conceitos fundamentais de backend como:

* APIs REST (CRUD)
* Spring Data JPA
* Arquitetura em camadas (Controller / Service / Repository)
* Modelagem de domínio
* Regras de negócio
* Testes de unidade
* Versionamento com Git/GitHub

---

## 🚀 Objetivo do Projeto

Construir um sistema de reserva de salas onde seja possível:

* Criar salas e usuários
* Realizar reservas de salas por usuários
* Validar conflitos de horário
* Garantir regras de negócio como capacidade e disponibilidade
* Cancelar reservas
* Manter consistência dos dados

---

## 🧱 Tecnologias utilizadas

* Java 17+
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Hibernate
* Maven
* JUnit

---

## 📦 Estrutura do Projeto

controller → service → repository → database

---

## 📁 Modelos de domínio

* Sala
* Usuario
* Reserva

---

## 🧩 Regras de negócio

### Sala

* Nome deve ser único
* Capacidade deve ser maior que zero

### Usuario

* Nome e e-mail como identificação básica

### Reserva

* Data de início deve ser anterior à data de fim
* Não pode existir sobreposição de reservas na mesma sala
* Reservas canceladas não entram em conflitos

---

## ⛔ Regra de conflito de horário

Intervalo semiaberto:

[ início, fim )

Exemplo:

* 10:00 → 12:00
* 12:00 → 14:00 ✔
* 11:00 → 13:00 ❌

---

## 🔄 Status da Reserva

* ATIVA
* CANCELADA

Reservas canceladas não participam de validações.

---

## 🧪 Testes

* Validação de regras de negócio
* Conflitos de horário
* Casos de borda

---

## 📌 Etapas

1. Modelagem de domínio
2. API CRUD
3. JPA
4. Testes
5. Git/GitHub

---

## 🎯 Aprendizados

* Modelagem de sistemas reais
* Backend com Spring Boot
* Regras de negócio
* Arquitetura em camadas
* Boas práticas de código

---

## 🚧 Status

Em desenvolvimento
