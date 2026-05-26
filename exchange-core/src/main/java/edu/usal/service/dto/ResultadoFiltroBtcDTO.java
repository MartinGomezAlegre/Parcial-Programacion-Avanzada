package edu.usal.service.dto;

import java.time.LocalDateTime;

public class ResultadoFiltroBtcDTO {

    private String direccionOrigen;
    private String direccionDestino;
    private double monto;
    private LocalDateTime fecha;

    public ResultadoFiltroBtcDTO(String direccionOrigen, String direccionDestino, double monto, LocalDateTime fecha) {
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
