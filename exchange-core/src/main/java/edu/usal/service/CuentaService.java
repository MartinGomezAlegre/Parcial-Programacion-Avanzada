package edu.usal.service;

import edu.usal.dao.CuentaDAO;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.exceptions.CuentaNoEncontradaException;
import edu.usal.modelo.cuentas.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class CuentaService {

    private final CuentaDAO cuentaDAO;

    public CuentaService(CuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
    }

    public boolean guardarCuenta(Cuenta cuenta) throws ArchivoPersistenciaException {
        return cuentaDAO.guardar(cuenta);
    }

    public boolean actualizarCuenta(Cuenta cuenta) throws ArchivoPersistenciaException {
        return cuentaDAO.actualizar(cuenta);
    }

    public Cuenta buscarPorNumero(String numero) throws ArchivoPersistenciaException, CuentaNoEncontradaException {
        return cuentaDAO.buscarPorNumero(numero)
                .orElseThrow(() -> new CuentaNoEncontradaException("No existe una cuenta con numero " + numero));
    }

    public List<Cuenta> obtenerTodas() throws ArchivoPersistenciaException {
        return cuentaDAO.obtenerTodas();
    }

    public List<Cuenta> obtenerPorNumeros(List<String> numerosCuenta) throws ArchivoPersistenciaException {
        List<Cuenta> cuentas = new ArrayList<>();

        for (String numeroCuenta : numerosCuenta) {
            cuentaDAO.buscarPorNumero(numeroCuenta).ifPresent(cuentas::add);
        }

        return cuentas;
    }

    public CuentaDAO getCuentaDAO() {
        return cuentaDAO;
    }
}
