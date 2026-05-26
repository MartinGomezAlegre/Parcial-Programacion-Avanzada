package edu.usal.dao;

import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.cuentas.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaDAO {

    boolean guardar(Cuenta cuenta) throws ArchivoPersistenciaException;

    boolean actualizar(Cuenta cuenta) throws ArchivoPersistenciaException;

    List<Cuenta> obtenerTodas() throws ArchivoPersistenciaException;

    Optional<Cuenta> buscarPorNumero(String numero) throws ArchivoPersistenciaException;
}
