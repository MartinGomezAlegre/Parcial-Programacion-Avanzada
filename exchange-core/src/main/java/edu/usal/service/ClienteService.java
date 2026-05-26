package edu.usal.service;

import edu.usal.dao.ClienteDAO;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.exceptions.ClienteNoEncontradoException;
import edu.usal.modelo.clientes.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;
    private List<Cliente> clientesEnMemoria;

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
        this.clientesEnMemoria = new ArrayList<>();
    }

    public void cargarClientesIniciales() throws ArchivoPersistenciaException {
        this.clientesEnMemoria = clienteDAO.obtenerTodos();
    }

    public boolean guardarCliente(Cliente cliente) throws ArchivoPersistenciaException {
        boolean guardado = clienteDAO.guardar(cliente);
        if (guardado) {
            this.clientesEnMemoria.add(cliente);
        }
        return guardado;
    }

    public List<Cliente> obtenerClientesEnMemoria() {
        return clientesEnMemoria;
    }

    public Cliente buscarPorCuit(String cuit) throws ClienteNoEncontradoException {
        return clientesEnMemoria.stream()
                .filter(cliente -> cliente.getCuit().equals(cuit))
                .findFirst()
                .orElseThrow(() -> new ClienteNoEncontradoException("No existe un cliente con CUIT " + cuit));
    }

    public boolean clientePoseeCuenta(String cuit, String numeroCuenta) throws ClienteNoEncontradoException {
        Cliente cliente = buscarPorCuit(cuit);
        return cliente.getNumerosCuenta().contains(numeroCuenta);
    }

    public boolean agregarCuentaACliente(String cuit, String numeroCuenta) throws ArchivoPersistenciaException, ClienteNoEncontradoException {
        Cliente cliente = buscarPorCuit(cuit);
        if (!cliente.getNumerosCuenta().contains(numeroCuenta)) {
            cliente.agregarNumeroCuenta(numeroCuenta);
            return clienteDAO.actualizar(cliente);
        }
        return false;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }
}
