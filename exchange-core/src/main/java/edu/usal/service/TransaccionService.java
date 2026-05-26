package edu.usal.service;

import edu.usal.dao.TransaccionDAO;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.cuentas.Cuenta;
import edu.usal.modelo.cuentas.CuentaBancaria;
import edu.usal.modelo.cuentas.TipoCrypto;
import edu.usal.modelo.cuentas.WalletCrypto;
import edu.usal.modelo.transacciones.Conversion;
import edu.usal.modelo.transacciones.Transaccion;
import edu.usal.modelo.transacciones.Transferencia;
import edu.usal.service.dto.ResultadoFiltroBtcDTO;
import edu.usal.service.dto.ResultadoFiltroFiatDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransaccionService {

    private final TransaccionDAO transaccionDAO;

    public TransaccionService(TransaccionDAO transaccionDAO) {
        this.transaccionDAO = transaccionDAO;
    }

    public boolean registrar(Transaccion transaccion) throws ArchivoPersistenciaException {
        return transaccionDAO.guardar(transaccion);
    }

    public List<Transaccion> obtenerHistorial() throws ArchivoPersistenciaException {
        return transaccionDAO.obtenerTodas();
    }

    public List<ResultadoFiltroFiatDTO> filtrarTransaccionesFiatMayoresA(double montoMinimo, CuentaService cuentaService)
            throws ArchivoPersistenciaException {
        Map<String, Cuenta> cuentas = cuentaService.obtenerTodas().stream()
                .collect(Collectors.toMap(Cuenta::getNumero, Function.identity()));

        return obtenerHistorial().stream()
                .map(transaccion -> mapearResultadoFiat(transaccion, cuentas))
                .filter(Objects::nonNull)
                .filter(resultado -> resultado.getMonto() > montoMinimo)
                .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                .collect(Collectors.toList());
    }

    public List<ResultadoFiltroBtcDTO> filtrarTransaccionesBtcPorFechas(LocalDate fechaDesde, LocalDate fechaHasta, CuentaService cuentaService)
            throws ArchivoPersistenciaException {
        Map<String, Cuenta> cuentas = cuentaService.obtenerTodas().stream()
                .collect(Collectors.toMap(Cuenta::getNumero, Function.identity()));

        return obtenerHistorial().stream()
                .map(transaccion -> mapearResultadoBtc(transaccion, cuentas))
                .filter(Objects::nonNull)
                .filter(resultado -> {
                    LocalDate fecha = resultado.getFecha().toLocalDate();
                    return !fecha.isBefore(fechaDesde) && !fecha.isAfter(fechaHasta);
                })
                .sorted((a, b) -> a.getFecha().compareTo(b.getFecha()))
                .collect(Collectors.toList());
    }

    public TransaccionDAO getTransaccionDAO() {
        return transaccionDAO;
    }

    private ResultadoFiltroFiatDTO mapearResultadoFiat(Transaccion transaccion, Map<String, Cuenta> cuentas) {
        if (transaccion instanceof Transferencia) {
            Transferencia transferencia = (Transferencia) transaccion;
            Cuenta cuentaOrigen = cuentas.get(transferencia.getNumeroCuentaOrigen());
            Cuenta cuentaDestino = cuentas.get(transferencia.getNumeroCuentaDestino());

            if (cuentaOrigen instanceof CuentaBancaria && cuentaDestino instanceof CuentaBancaria) {
                CuentaBancaria origen = (CuentaBancaria) cuentaOrigen;
                CuentaBancaria destino = (CuentaBancaria) cuentaDestino;
                return new ResultadoFiltroFiatDTO(
                        origen.getCuit(),
                        origen.getCbu(),
                        destino.getCuit(),
                        destino.getCbu(),
                        transferencia.getMonto(),
                        transferencia.getFecha()
                );
            }
        }

        if (transaccion instanceof Conversion) {
            Conversion conversion = (Conversion) transaccion;
            Cuenta cuentaOrigen = cuentas.get(conversion.getNumeroCuentaOrigen());
            Cuenta cuentaDestino = cuentas.get(conversion.getNumeroCuentaDestino());

            if (cuentaOrigen instanceof CuentaBancaria && cuentaDestino instanceof CuentaBancaria) {
                CuentaBancaria origen = (CuentaBancaria) cuentaOrigen;
                CuentaBancaria destino = (CuentaBancaria) cuentaDestino;
                return new ResultadoFiltroFiatDTO(
                        origen.getCuit(),
                        origen.getCbu(),
                        destino.getCuit(),
                        destino.getCbu(),
                        conversion.getMonto(),
                        conversion.getFecha()
                );
            }
        }

        return null;
    }

    private ResultadoFiltroBtcDTO mapearResultadoBtc(Transaccion transaccion, Map<String, Cuenta> cuentas) {
        if (transaccion instanceof Transferencia) {
            Transferencia transferencia = (Transferencia) transaccion;
            Cuenta cuentaOrigen = cuentas.get(transferencia.getNumeroCuentaOrigen());
            Cuenta cuentaDestino = cuentas.get(transferencia.getNumeroCuentaDestino());

            if (cuentaOrigen instanceof WalletCrypto && cuentaDestino instanceof WalletCrypto) {
                WalletCrypto origen = (WalletCrypto) cuentaOrigen;
                WalletCrypto destino = (WalletCrypto) cuentaDestino;
                if (origen.getTipoCrypto() == TipoCrypto.BTC) {
                    return new ResultadoFiltroBtcDTO(
                            origen.getDireccion(),
                            destino.getDireccion(),
                            transferencia.getMonto(),
                            transferencia.getFecha()
                    );
                }
            }
        }

        if (transaccion instanceof Conversion) {
            Conversion conversion = (Conversion) transaccion;
            Cuenta cuentaOrigen = cuentas.get(conversion.getNumeroCuentaOrigen());
            Cuenta cuentaDestino = cuentas.get(conversion.getNumeroCuentaDestino());

            if (cuentaOrigen instanceof WalletCrypto && cuentaDestino instanceof WalletCrypto) {
                WalletCrypto origen = (WalletCrypto) cuentaOrigen;
                WalletCrypto destino = (WalletCrypto) cuentaDestino;
                if (origen.getTipoCrypto() == TipoCrypto.BTC) {
                    return new ResultadoFiltroBtcDTO(
                            origen.getDireccion(),
                            destino.getDireccion(),
                            conversion.getMonto(),
                            conversion.getFecha()
                    );
                }
            }
        }

        return null;
    }
}
