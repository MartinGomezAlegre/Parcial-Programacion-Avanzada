package edu.usal.dao;

import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.clientes.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO {

    boolean guardar(Cliente cliente) throws ArchivoPersistenciaException;

    boolean actualizar(Cliente cliente) throws ArchivoPersistenciaException;

    List<Cliente> obtenerTodos() throws ArchivoPersistenciaException;

    Optional<Cliente> buscarPorCuit(String cuit) throws ArchivoPersistenciaException;
}
