package com.reservas.room.enums;

public enum StatusReserva {
    PENDENTE,
    ATIVA,
    CANCELADA,
    FINALIZADA;

    /**
     * Transições permitidas:
     * PENDENTE -> ATIVA (confirmar)
     * PENDENTE -> CANCELADA (cancelar antes de confirmar)
     * ATIVA -> CANCELADA (cancelar)
     * ATIVA -> FINALIZADA (concluir)
     *
     * CANCELADA e FINALIZADA são estados finais (não mudam mais)
     */
    public boolean podeTransitarPara(StatusReserva novoStatus) {
        return switch (this) {
            case PENDENTE -> novoStatus == ATIVA || novoStatus == CANCELADA;
            case ATIVA -> novoStatus == CANCELADA || novoStatus == FINALIZADA;
            case CANCELADA, FINALIZADA -> false;
        };
    }

}
