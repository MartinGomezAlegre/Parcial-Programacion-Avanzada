package edu.usal.factory;

import edu.usal.config.AppProperties;
import edu.usal.dao.ClienteDAO;
import edu.usal.dao.CuentaDAO;
import edu.usal.dao.TransaccionDAO;
import edu.usal.dao.impl.ClienteDAOImplString;
import edu.usal.dao.impl.CuentaDAOImplByte;
import edu.usal.dao.impl.TransaccionDAOImplString;

public final class DAOFactory {

    private DAOFactory() {
    }

    public static ClienteDAO getClienteDAO() {
        String origen = AppProperties.get("dao.cliente");
        if ("STRING".equalsIgnoreCase(origen)) {
            return new ClienteDAOImplString();
        }
        throw new IllegalStateException("Implementacion de ClienteDAO inexistente");
    }

    public static CuentaDAO getCuentaDAO() {
        String origen = AppProperties.get("dao.cuenta");
        if ("BYTE".equalsIgnoreCase(origen)) {
            return new CuentaDAOImplByte();
        }
        throw new IllegalStateException("Implementacion de CuentaDAO inexistente");
    }

    public static TransaccionDAO getTransaccionDAO() {
        String origen = AppProperties.get("dao.transaccion");
        if ("STRING".equalsIgnoreCase(origen)) {
            return new TransaccionDAOImplString();
        }
        throw new IllegalStateException("Implementacion de TransaccionDAO inexistente");
    }
}
