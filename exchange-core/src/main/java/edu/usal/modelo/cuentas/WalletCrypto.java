package edu.usal.modelo.cuentas;

public class WalletCrypto extends Cuenta {

    private String direccion;
    private TipoCrypto tipoCrypto;

    public WalletCrypto() {
    }

    public WalletCrypto(String numero, double saldo, String direccion, TipoCrypto tipoCrypto) {
        super(numero, saldo);
        this.direccion = direccion;
        this.tipoCrypto = tipoCrypto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoCrypto getTipoCrypto() {
        return tipoCrypto;
    }

    public void setTipoCrypto(TipoCrypto tipoCrypto) {
        this.tipoCrypto = tipoCrypto;
    }

    @Override
    public String toString() {
        return "WalletCrypto{" +
                "numero='" + getNumero() + '\'' +
                ", saldo=" + getSaldo() +
                ", direccion='" + direccion + '\'' +
                ", tipoCrypto=" + tipoCrypto +
                '}';
    }
}
