package edu.usal.dao.impl;

import edu.usal.config.AppProperties;
import edu.usal.dao.TransaccionDAO;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.transacciones.Conversion;
import edu.usal.modelo.transacciones.Deposito;
import edu.usal.modelo.transacciones.Extraccion;
import edu.usal.modelo.transacciones.Transaccion;
import edu.usal.modelo.transacciones.Transferencia;
import edu.usal.persistence.ArchivoManagerString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAOImplString implements TransaccionDAO {

    private final ArchivoManagerString<Deposito> depositoManager;
    private final ArchivoManagerString<Extraccion> extraccionManager;
    private final ArchivoManagerString<Transferencia> transferenciaManager;
    private final ArchivoManagerString<Conversion> conversionManager;

    public TransaccionDAOImplString() {
        this.depositoManager = new ArchivoManagerString<>(AppProperties.get("ruta.depositos")) {
            @Override
            protected String convertirARegistro(Deposito entidad) {
                return entidad.getId() + ";" +
                        entidad.getCuitCliente() + ";" +
                        entidad.getFecha() + ";" +
                        entidad.getMonto() + ";" +
                        entidad.getNumeroCuentaDestino();
            }

            @Override
            protected Deposito convertirDesdeRegistro(String registro) {
                String[] partes = registro.split(";", -1);
                return new Deposito(
                        partes[0],
                        partes[1],
                        LocalDateTime.parse(partes[2]),
                        Double.parseDouble(partes[3]),
                        partes[4]
                );
            }
        };

        this.extraccionManager = new ArchivoManagerString<>(AppProperties.get("ruta.extracciones")) {
            @Override
            protected String convertirARegistro(Extraccion entidad) {
                return entidad.getId() + ";" +
                        entidad.getCuitCliente() + ";" +
                        entidad.getFecha() + ";" +
                        entidad.getMonto() + ";" +
                        entidad.getNumeroCuentaOrigen();
            }

            @Override
            protected Extraccion convertirDesdeRegistro(String registro) {
                String[] partes = registro.split(";", -1);
                return new Extraccion(
                        partes[0],
                        partes[1],
                        LocalDateTime.parse(partes[2]),
                        Double.parseDouble(partes[3]),
                        partes[4]
                );
            }
        };

        this.transferenciaManager = new ArchivoManagerString<>(AppProperties.get("ruta.transferencias")) {
            @Override
            protected String convertirARegistro(Transferencia entidad) {
                return entidad.getId() + ";" +
                        entidad.getCuitCliente() + ";" +
                        entidad.getFecha() + ";" +
                        entidad.getMonto() + ";" +
                        entidad.getNumeroCuentaOrigen() + ";" +
                        entidad.getNumeroCuentaDestino();
            }

            @Override
            protected Transferencia convertirDesdeRegistro(String registro) {
                String[] partes = registro.split(";", -1);
                return new Transferencia(
                        partes[0],
                        partes[1],
                        LocalDateTime.parse(partes[2]),
                        Double.parseDouble(partes[3]),
                        partes[4],
                        partes[5]
                );
            }
        };

        this.conversionManager = new ArchivoManagerString<>(AppProperties.get("ruta.conversiones")) {
            @Override
            protected String convertirARegistro(Conversion entidad) {
                return entidad.getId() + ";" +
                        entidad.getCuitCliente() + ";" +
                        entidad.getFecha() + ";" +
                        entidad.getMonto() + ";" +
                        entidad.getNumeroCuentaOrigen() + ";" +
                        entidad.getNumeroCuentaDestino() + ";" +
                        entidad.getCotizacionAplicada();
            }

            @Override
            protected Conversion convertirDesdeRegistro(String registro) {
                String[] partes = registro.split(";", -1);
                return new Conversion(
                        partes[0],
                        partes[1],
                        LocalDateTime.parse(partes[2]),
                        Double.parseDouble(partes[3]),
                        partes[4],
                        partes[5],
                        Double.parseDouble(partes[6])
                );
            }
        };
    }

    @Override
    public boolean guardar(Transaccion transaccion) throws ArchivoPersistenciaException {
        if (transaccion instanceof Deposito) {
            depositoManager.guardarRegistro((Deposito) transaccion);
            return true;
        }
        if (transaccion instanceof Extraccion) {
            extraccionManager.guardarRegistro((Extraccion) transaccion);
            return true;
        }
        if (transaccion instanceof Transferencia) {
            transferenciaManager.guardarRegistro((Transferencia) transaccion);
            return true;
        }
        if (transaccion instanceof Conversion) {
            conversionManager.guardarRegistro((Conversion) transaccion);
            return true;
        }
        return false;
    }

    @Override
    public List<Transaccion> obtenerTodas() throws ArchivoPersistenciaException {
        List<Transaccion> transacciones = new ArrayList<>();
        transacciones.addAll(depositoManager.leerRegistros());
        transacciones.addAll(extraccionManager.leerRegistros());
        transacciones.addAll(transferenciaManager.leerRegistros());
        transacciones.addAll(conversionManager.leerRegistros());
        return transacciones;
    }
}
