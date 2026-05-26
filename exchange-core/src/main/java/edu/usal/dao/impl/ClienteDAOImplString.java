package edu.usal.dao.impl;

import edu.usal.config.AppProperties;
import edu.usal.dao.ClienteDAO;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.clientes.Cliente;
import edu.usal.modelo.clientes.DatosContacto;
import edu.usal.persistence.ArchivoManagerString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClienteDAOImplString extends ArchivoManagerString<Cliente> implements ClienteDAO {

    private static final String SEPARATOR = ";";
    private static final String CUENTAS_SEPARATOR = "\\|";
    private static final String CUENTAS_JOIN_SEPARATOR = "|";

    public ClienteDAOImplString() {
        super(AppProperties.get("ruta.clientes"));
    }

    @Override
    public boolean guardar(Cliente cliente) throws ArchivoPersistenciaException {
        List<Cliente> clientes = obtenerTodos();
        clientes.add(cliente);
        return guardarTodos(clientes);
    }

    @Override
    public boolean actualizar(Cliente cliente) throws ArchivoPersistenciaException {
        List<Cliente> clientes = obtenerTodos();
        boolean actualizado = false;

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCuit().equals(cliente.getCuit())) {
                clientes.set(i, cliente);
                actualizado = true;
                break;
            }
        }

        if (!actualizado) {
            return false;
        }

        return guardarTodos(clientes);
    }

    @Override
    public List<Cliente> obtenerTodos() throws ArchivoPersistenciaException {
        return leerRegistros();
    }

    @Override
    public Optional<Cliente> buscarPorCuit(String cuit) throws ArchivoPersistenciaException {
        return obtenerTodos().stream()
                .filter(cliente -> cliente.getCuit().equals(cuit))
                .findFirst();
    }

    @Override
    protected String convertirARegistro(Cliente entidad) {
        DatosContacto datosContacto = entidad.getDatosContacto();
        if (datosContacto == null) {
            datosContacto = new DatosContacto();
        }
        String cuentas = entidad.getNumerosCuenta() == null || entidad.getNumerosCuenta().isEmpty()
                ? ""
                : String.join(CUENTAS_JOIN_SEPARATOR, entidad.getNumerosCuenta());

        return entidad.getNombre() + SEPARATOR +
                entidad.getApellido() + SEPARATOR +
                entidad.getCuit() + SEPARATOR +
                valorSeguro(datosContacto.getTelefonoParticular()) + SEPARATOR +
                valorSeguro(datosContacto.getCelular()) + SEPARATOR +
                valorSeguro(datosContacto.getEmail()) + SEPARATOR +
                valorSeguro(datosContacto.getDomicilio()) + SEPARATOR +
                cuentas;
    }

    @Override
    protected Cliente convertirDesdeRegistro(String registro) {
        String[] partes = registro.split(SEPARATOR, -1);

        DatosContacto datosContacto = new DatosContacto(
                partes[3],
                partes[4],
                partes[5],
                partes[6]
        );

        List<String> numerosCuenta = new ArrayList<>();
        if (partes.length > 7 && !partes[7].isBlank()) {
            numerosCuenta = Arrays.stream(partes[7].split(CUENTAS_SEPARATOR))
                    .collect(Collectors.toList());
        }

        return new Cliente(partes[0], partes[1], partes[2], datosContacto, numerosCuenta);
    }

    private boolean guardarTodos(List<Cliente> clientes) throws ArchivoPersistenciaException {
        reescribirRegistros(clientes);
        return true;
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }
}
