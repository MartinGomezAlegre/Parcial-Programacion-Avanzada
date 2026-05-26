package edu.usal.service.dto;

import java.time.LocalDateTime;

public class ResultadoFiltroFiatDTO {

    private String cuitOrigen;
    private String cbuOrigen;
    private String cuitDestino;
    private String cbuDestino;
    private double monto;
    private LocalDateTime fecha;

    public ResultadoFiltroFiatDTO(String cuitOrigen, String cbuOrigen, String cuitDestino, String cbuDestino, double monto, LocalDateTime fecha) {
        this.cuitOrigen = cuitOrigen;
        this.cbuOrigen = cbuOrigen;
        this.cuitDestino = cuitDestino;
        this.cbuDestino = cbuDestino;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getCuitOrigen() {
        return cuitOrigen;
    }

    public String getCbuOrigen() {
        return cbuOrigen;
    }

    public String getCuitDestino() {
        return cuitDestino;
    }

    public String getCbuDestino() {
        return cbuDestino;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
