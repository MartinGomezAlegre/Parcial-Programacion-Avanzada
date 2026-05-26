package edu.usal.factory;

import edu.usal.modelo.cuentas.CajaAhorro;
import edu.usal.modelo.cuentas.CuentaCorriente;
import edu.usal.modelo.cuentas.TipoCrypto;
import edu.usal.modelo.cuentas.TipoMoneda;
import edu.usal.modelo.cuentas.WalletCrypto;

public final class CuentaFactory {

    private CuentaFactory() {
    }

    public static CajaAhorro crearCajaAhorro(String numero, double saldo, String cbu, String cuit, TipoMoneda tipoMoneda) {
        return new CajaAhorro(numero, saldo, cbu, cuit, tipoMoneda);
    }

    public static CuentaCorriente crearCuentaCorriente(String numero, double saldo, String cbu, String cuit, TipoMoneda tipoMoneda, double descubierto) {
        return new CuentaCorriente(numero, saldo, cbu, cuit, tipoMoneda, descubierto);
    }

    public static WalletCrypto crearWallet(String numero, double saldo, String direccion, TipoCrypto tipoCrypto) {
        return new WalletCrypto(numero, saldo, direccion, tipoCrypto);
    }
}
