package edu.usal.dao.impl;

import edu.usal.config.AppProperties;
import edu.usal.dao.CuentaDAO;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.cuentas.Cuenta;
import edu.usal.persistence.ArchivoManagerByte;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuentaDAOImplByte implements CuentaDAO {

    private final ArchivoManagerByte<Cuenta> archivoManagerByte;

    public CuentaDAOImplByte() {
        this.archivoManagerByte = new ArchivoManagerByte<>(AppProperties.get("ruta.cuentas"));
    }

    @Override
    public boolean guardar(Cuenta cuenta) throws ArchivoPersistenciaException {
        List<Cuenta> cuentas = obtenerTodas();
        cuentas.add(cuenta);
        archivoManagerByte.guardarLista(cuentas);
        return true;
    }

    @Override
    public boolean actualizar(Cuenta cuenta) throws ArchivoPersistenciaException {
        List<Cuenta> cuentas = obtenerTodas();
        boolean actualizada = false;

        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getNumero().equals(cuenta.getNumero())) {
                cuentas.set(i, cuenta);
                actualizada = true;
                break;
            }
        }

        if (!actualizada) {
            return false;
        }

        archivoManagerByte.guardarLista(cuentas);
        return true;
    }

    @Override
    public List<Cuenta> obtenerTodas() throws ArchivoPersistenciaException {
        return new ArrayList<>(archivoManagerByte.leerLista());
    }

    @Override
    public Optional<Cuenta> buscarPorNumero(String numero) throws ArchivoPersistenciaException {
        return obtenerTodas().stream()
                .filter(cuenta -> cuenta.getNumero().equals(numero))
                .findFirst();
    }
}
