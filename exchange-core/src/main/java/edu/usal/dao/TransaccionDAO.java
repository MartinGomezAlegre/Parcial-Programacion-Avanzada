package edu.usal.dao;

import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.transacciones.Transaccion;

import java.util.List;

public interface TransaccionDAO {

    boolean guardar(Transaccion transaccion) throws ArchivoPersistenciaException;

    List<Transaccion> obtenerTodas() throws ArchivoPersistenciaException;
}
