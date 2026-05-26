package edu.usal.modelo.transacciones;

import java.time.LocalDateTime;

public class Conversion extends Transaccion {

    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private double cotizacionAplicada;

    public Conversion() {
    }

    public Conversion(String id, String cuitCliente, LocalDateTime fecha, double monto, String numeroCuentaOrigen, String numeroCuentaDestino, double cotizacionAplicada) {
        super(id, cuitCliente, fecha, monto);
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
        this.cotizacionAplicada = cotizacionAplicada;
    }

    public String getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    public void setNumeroCuentaOrigen(String numeroCuentaOrigen) {
        this.numeroCuentaOrigen = numeroCuentaOrigen;
    }

    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    public void setNumeroCuentaDestino(String numeroCuentaDestino) {
        this.numeroCuentaDestino = numeroCuentaDestino;
    }

    public double getCotizacionAplicada() {
        return cotizacionAplicada;
    }

    public void setCotizacionAplicada(double cotizacionAplicada) {
        this.cotizacionAplicada = cotizacionAplicada;
    }
}
