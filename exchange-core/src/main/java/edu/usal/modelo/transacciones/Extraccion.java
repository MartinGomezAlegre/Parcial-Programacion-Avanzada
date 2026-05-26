package edu.usal.modelo.transacciones;

import java.time.LocalDateTime;

public class Extraccion extends Transaccion {

    private String numeroCuentaOrigen;

    public Extraccion() {
    }

    public Extraccion(String id, String cuitCliente, LocalDateTime fecha, double monto, String numeroCuentaOrigen) {
        super(id, cuitCliente, fecha, monto);
        this.numeroCuentaOrigen = numeroCuentaOrigen;
    }

    public String getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    public void setNumeroCuentaOrigen(String numeroCuentaOrigen) {
        this.numeroCuentaOrigen = numeroCuentaOrigen;
    }
}
