package edu.usal.modelo.cuentas;

public class CuentaCorriente extends CuentaBancaria {

    private double descubierto;

    public CuentaCorriente() {
    }

    public CuentaCorriente(String numero, double saldo, String cbu, String cuit, TipoMoneda tipoMoneda, double descubierto) {
        super(numero, saldo, cbu, cuit, tipoMoneda);
        this.descubierto = descubierto;
    }

    public double getDescubierto() {
        return descubierto;
    }

    public void setDescubierto(double descubierto) {
        this.descubierto = descubierto;
    }

    @Override
    public String toString() {
        return "CuentaCorriente{" +
                "numero='" + getNumero() + '\'' +
                ", saldo=" + getSaldo() +
                ", cbu='" + getCbu() + '\'' +
                ", cuit='" + getCuit() + '\'' +
                ", tipoMoneda=" + getTipoMoneda() +
                ", descubierto=" + descubierto +
                '}';
    }
}
