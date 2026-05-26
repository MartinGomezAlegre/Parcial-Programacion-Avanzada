package edu.usal.factory;

import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.clientes.Cliente;
import edu.usal.modelo.clientes.DatosContacto;
import edu.usal.modelo.cuentas.TipoCrypto;
import edu.usal.modelo.cuentas.TipoMoneda;
import edu.usal.service.ClienteService;
import edu.usal.service.CuentaService;
import edu.usal.service.ExchangeService;
import edu.usal.service.TransaccionService;

import java.util.Arrays;

public final class ServiceFactory {

    private ServiceFactory() {
    }

    public static ExchangeService crearExchangeService() throws ArchivoPersistenciaException {
        ClienteService clienteService = new ClienteService(DAOFactory.getClienteDAO());
        clienteService.cargarClientesIniciales();

        CuentaService cuentaService = new CuentaService(DAOFactory.getCuentaDAO());
        TransaccionService transaccionService = new TransaccionService(DAOFactory.getTransaccionDAO());

        cargarDatosDemoSiNoExisten(clienteService, cuentaService);

        return new ExchangeService(clienteService, cuentaService, transaccionService);
    }

    private static void cargarDatosDemoSiNoExisten(ClienteService clienteService, CuentaService cuentaService) throws ArchivoPersistenciaException {
        if (!clienteService.obtenerClientesEnMemoria().isEmpty() || !cuentaService.obtenerTodas().isEmpty()) {
            return;
        }

        Cliente juan = new Cliente(
                "Juan",
                "Perez",
                "20-11111111-3",
                new DatosContacto("4371-0000", "11-5555-1111", "juan@exchange.com", "Siempre Viva 123"),
                Arrays.asList("CA-001", "CC-001", "BTC-001", "ETH-001")
        );

        Cliente ana = new Cliente(
                "Ana",
                "Gomez",
                "27-22222222-5",
                new DatosContacto("4365-0000", "11-6666-2222", "ana@exchange.com", "Lavalle 456"),
                Arrays.asList("CA-002", "USD-001", "BTC-002")
        );

        clienteService.guardarCliente(juan);
        clienteService.guardarCliente(ana);

        cuentaService.guardarCuenta(CuentaFactory.crearCajaAhorro("CA-001", 150000, "CBU-AR-001", juan.getCuit(), TipoMoneda.PESOS));
        cuentaService.guardarCuenta(CuentaFactory.crearCuentaCorriente("CC-001", 50000, "CBU-AR-002", juan.getCuit(), TipoMoneda.PESOS, 20000));
        cuentaService.guardarCuenta(CuentaFactory.crearWallet("BTC-001", 1.5, "DIR-BTC-001", TipoCrypto.BTC));
        cuentaService.guardarCuenta(CuentaFactory.crearWallet("ETH-001", 10, "DIR-ETH-001", TipoCrypto.ETH));

        cuentaService.guardarCuenta(CuentaFactory.crearCajaAhorro("CA-002", 90000, "CBU-AR-003", ana.getCuit(), TipoMoneda.PESOS));
        cuentaService.guardarCuenta(CuentaFactory.crearCajaAhorro("USD-001", 1200, "CBU-AR-004", ana.getCuit(), TipoMoneda.DOLARES));
        cuentaService.guardarCuenta(CuentaFactory.crearWallet("BTC-002", 0.8, "DIR-BTC-002", TipoCrypto.BTC));
    }
}
