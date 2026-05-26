package edu.usal.modelo.cuentas;

public class CajaAhorro extends CuentaBancaria {

    public CajaAhorro() {
    }

    public CajaAhorro(String numero, double saldo, String cbu, String cuit, TipoMoneda tipoMoneda) {
        super(numero, saldo, cbu, cuit, tipoMoneda);
    }
}
