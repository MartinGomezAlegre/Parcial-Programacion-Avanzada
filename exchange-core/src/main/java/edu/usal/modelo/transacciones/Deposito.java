package edu.usal.modelo.transacciones;

import java.time.LocalDateTime;

public class Deposito extends Transaccion {

    private String numeroCuentaDestino;

    public Deposito() {
    }

    public Deposito(String id, String cuitCliente, LocalDateTime fecha, double monto, String numeroCuentaDestino) {
        super(id, cuitCliente, fecha, monto);
        this.numeroCuentaDestino = numeroCuentaDestino;
    }

    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    public void setNumeroCuentaDestino(String numeroCuentaDestino) {
        this.numeroCuentaDestino = numeroCuentaDestino;
    }
}
