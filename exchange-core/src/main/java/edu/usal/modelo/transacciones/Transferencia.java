package edu.usal.modelo.transacciones;

import java.time.LocalDateTime;

public class Transferencia extends Transaccion {

    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;

    public Transferencia() {
    }

    public Transferencia(String id, String cuitCliente, LocalDateTime fecha, double monto, String numeroCuentaOrigen, String numeroCuentaDestino) {
        super(id, cuitCliente, fecha, monto);
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
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
}
