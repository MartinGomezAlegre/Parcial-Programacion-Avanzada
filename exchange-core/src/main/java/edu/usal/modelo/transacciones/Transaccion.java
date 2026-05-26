package edu.usal.modelo.transacciones;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Transaccion implements Serializable {

    private String id;
    private String cuitCliente;
    private LocalDateTime fecha;
    private double monto;

    public Transaccion() {
        this.fecha = LocalDateTime.now();
    }

    public Transaccion(String id, String cuitCliente, LocalDateTime fecha, double monto) {
        this.id = id;
        this.cuitCliente = cuitCliente;
        this.fecha = fecha;
        this.monto = monto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuitCliente() {
        return cuitCliente;
    }

    public void setCuitCliente(String cuitCliente) {
        this.cuitCliente = cuitCliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
