package edu.usal.modelo.cuentas;

public abstract class CuentaBancaria extends Cuenta {

    private String cbu;
    private String cuit;
    private TipoMoneda tipoMoneda;

    public CuentaBancaria() {
    }

    public CuentaBancaria(String numero, double saldo, String cbu, String cuit, TipoMoneda tipoMoneda) {
        super(numero, saldo);
        this.cbu = cbu;
        this.cuit = cuit;
        this.tipoMoneda = tipoMoneda;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "numero='" + getNumero() + '\'' +
                ", saldo=" + getSaldo() +
                ", cbu='" + cbu + '\'' +
                ", cuit='" + cuit + '\'' +
                ", tipoMoneda=" + tipoMoneda +
                '}';
    }
}
