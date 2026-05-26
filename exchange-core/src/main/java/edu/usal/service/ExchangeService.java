package edu.usal.service;

import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.exceptions.ClienteNoEncontradoException;
import edu.usal.exceptions.CuentaNoEncontradaException;
import edu.usal.exceptions.OperacionInvalidaException;
import edu.usal.exceptions.SaldoInsuficienteException;
import edu.usal.modelo.cuentas.Cuenta;
import edu.usal.modelo.cuentas.CuentaCorriente;
import edu.usal.modelo.transacciones.Conversion;
import edu.usal.modelo.transacciones.Deposito;
import edu.usal.modelo.transacciones.Extraccion;
import edu.usal.modelo.transacciones.Transferencia;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExchangeService {

    private final ClienteService clienteService;
    private final CuentaService cuentaService;
    private final TransaccionService transaccionService;

    public ExchangeService(ClienteService clienteService, CuentaService cuentaService, TransaccionService transaccionService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.transaccionService = transaccionService;
    }

    public void depositar(String cuitCliente, String numeroCuentaDestino, double monto)
            throws ClienteNoEncontradoException, CuentaNoEncontradaException, OperacionInvalidaException, ArchivoPersistenciaException {
        validarMonto(monto);
        validarClientePoseeCuenta(cuitCliente, numeroCuentaDestino);

        Cuenta cuentaDestino = cuentaService.buscarPorNumero(numeroCuentaDestino);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
        cuentaService.actualizarCuenta(cuentaDestino);

        Deposito deposito = new Deposito(
                generarId(),
                cuitCliente,
                LocalDateTime.now(),
                monto,
                numeroCuentaDestino
        );
        transaccionService.registrar(deposito);
    }

    public void extraer(String cuitCliente, String numeroCuentaOrigen, double monto)
            throws ClienteNoEncontradoException, CuentaNoEncontradaException, OperacionInvalidaException, SaldoInsuficienteException, ArchivoPersistenciaException {
        validarMonto(monto);
        validarClientePoseeCuenta(cuitCliente, numeroCuentaOrigen);

        Cuenta cuentaOrigen = cuentaService.buscarPorNumero(numeroCuentaOrigen);
        debitarCuenta(cuentaOrigen, monto);
        cuentaService.actualizarCuenta(cuentaOrigen);

        Extraccion extraccion = new Extraccion(
                generarId(),
                cuitCliente,
                LocalDateTime.now(),
                monto,
                numeroCuentaOrigen
        );
        transaccionService.registrar(extraccion);
    }

    public void transferir(String cuitCliente, String numeroCuentaOrigen, String numeroCuentaDestino, double monto)
            throws ClienteNoEncontradoException, CuentaNoEncontradaException, OperacionInvalidaException, SaldoInsuficienteException, ArchivoPersistenciaException {
        validarMonto(monto);
        validarCuentasDistintas(numeroCuentaOrigen, numeroCuentaDestino);
        validarClientePoseeCuenta(cuitCliente, numeroCuentaOrigen);

        Cuenta cuentaOrigen = cuentaService.buscarPorNumero(numeroCuentaOrigen);
        Cuenta cuentaDestino = cuentaService.buscarPorNumero(numeroCuentaDestino);

        debitarCuenta(cuentaOrigen, monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

        cuentaService.actualizarCuenta(cuentaOrigen);
        cuentaService.actualizarCuenta(cuentaDestino);

        Transferencia transferencia = new Transferencia(
                generarId(),
                cuitCliente,
                LocalDateTime.now(),
                monto,
                numeroCuentaOrigen,
                numeroCuentaDestino
        );
        transaccionService.registrar(transferencia);
    }

    public void convertir(String cuitCliente, String numeroCuentaOrigen, String numeroCuentaDestino, double montoOrigen, double cotizacionAplicada)
            throws ClienteNoEncontradoException, CuentaNoEncontradaException, OperacionInvalidaException, SaldoInsuficienteException, ArchivoPersistenciaException {
        validarMonto(montoOrigen);
        if (cotizacionAplicada <= 0) {
            throw new OperacionInvalidaException("La cotizacion aplicada debe ser mayor a cero");
        }
        validarCuentasDistintas(numeroCuentaOrigen, numeroCuentaDestino);
        validarClientePoseeCuenta(cuitCliente, numeroCuentaOrigen);

        Cuenta cuentaOrigen = cuentaService.buscarPorNumero(numeroCuentaOrigen);
        Cuenta cuentaDestino = cuentaService.buscarPorNumero(numeroCuentaDestino);

        double montoConvertido = montoOrigen * cotizacionAplicada;
        debitarCuenta(cuentaOrigen, montoOrigen);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + montoConvertido);

        cuentaService.actualizarCuenta(cuentaOrigen);
        cuentaService.actualizarCuenta(cuentaDestino);

        Conversion conversion = new Conversion(
                generarId(),
                cuitCliente,
                LocalDateTime.now(),
                montoOrigen,
                numeroCuentaOrigen,
                numeroCuentaDestino,
                cotizacionAplicada
        );
        transaccionService.registrar(conversion);
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public CuentaService getCuentaService() {
        return cuentaService;
    }

    public TransaccionService getTransaccionService() {
        return transaccionService;
    }

    private void validarMonto(double monto) throws OperacionInvalidaException {
        if (monto <= 0) {
            throw new OperacionInvalidaException("El monto debe ser mayor a cero");
        }
    }

    private void validarCuentasDistintas(String numeroCuentaOrigen, String numeroCuentaDestino) throws OperacionInvalidaException {
        if (numeroCuentaOrigen.equals(numeroCuentaDestino)) {
            throw new OperacionInvalidaException("La cuenta origen y destino no pueden ser la misma");
        }
    }

    private void validarClientePoseeCuenta(String cuitCliente, String numeroCuenta)
            throws ClienteNoEncontradoException, OperacionInvalidaException {
        if (!clienteService.clientePoseeCuenta(cuitCliente, numeroCuenta)) {
            throw new OperacionInvalidaException("La cuenta " + numeroCuenta + " no pertenece al cliente " + cuitCliente);
        }
    }

    private void debitarCuenta(Cuenta cuenta, double monto) throws SaldoInsuficienteException {
        if (cuenta instanceof CuentaCorriente) {
            CuentaCorriente cuentaCorriente = (CuentaCorriente) cuenta;
            if (cuentaCorriente.getSaldo() + cuentaCorriente.getDescubierto() < monto) {
                throw new SaldoInsuficienteException("Saldo insuficiente para operar");
            }
            cuentaCorriente.setSaldo(cuentaCorriente.getSaldo() - monto);
            return;
        }

        if (cuenta.getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente para operar");
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto);
    }

    private String generarId() {
        return UUID.randomUUID().toString();
    }
}
