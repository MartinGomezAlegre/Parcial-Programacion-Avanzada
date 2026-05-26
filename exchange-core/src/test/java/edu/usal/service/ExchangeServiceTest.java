package edu.usal.service;

import edu.usal.dao.ClienteDAO;
import edu.usal.dao.CuentaDAO;
import edu.usal.dao.TransaccionDAO;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.modelo.clientes.Cliente;
import edu.usal.modelo.clientes.DatosContacto;
import edu.usal.modelo.cuentas.CajaAhorro;
import edu.usal.modelo.cuentas.Cuenta;
import edu.usal.modelo.cuentas.CuentaCorriente;
import edu.usal.modelo.cuentas.TipoCrypto;
import edu.usal.modelo.cuentas.TipoMoneda;
import edu.usal.modelo.cuentas.WalletCrypto;
import edu.usal.modelo.transacciones.Transaccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeServiceTest {

    private ExchangeService exchangeService;
    private ClienteService clienteService;
    private CuentaService cuentaService;
    private TransaccionService transaccionService;

    @BeforeEach
    void setUp() throws ArchivoPersistenciaException {
        ClienteDAOMemory clienteDAO = new ClienteDAOMemory();
        CuentaDAOMemory cuentaDAO = new CuentaDAOMemory();
        TransaccionDAOMemory transaccionDAO = new TransaccionDAOMemory();

        Cliente cliente = new Cliente(
                "Juan",
                "Perez",
                "20-11111111-3",
                new DatosContacto("43710000", "1155555555", "juan@mail.com", "Siempre Viva 123")
        );
        cliente.agregarNumeroCuenta("CA-001");
        cliente.agregarNumeroCuenta("CC-001");
        cliente.agregarNumeroCuenta("BTC-001");
        cliente.agregarNumeroCuenta("ETH-001");

        clienteDAO.guardar(cliente);

        cuentaDAO.guardar(new CajaAhorro("CA-001", 1000, "CBU-001", "20-11111111-3", TipoMoneda.PESOS));
        cuentaDAO.guardar(new CuentaCorriente("CC-001", 2000, "CBU-002", "20-11111111-3", TipoMoneda.PESOS, 500));
        cuentaDAO.guardar(new WalletCrypto("BTC-001", 2, "DIR-BTC-001", TipoCrypto.BTC));
        cuentaDAO.guardar(new WalletCrypto("ETH-001", 0, "DIR-ETH-001", TipoCrypto.ETH));

        clienteService = new ClienteService(clienteDAO);
        clienteService.cargarClientesIniciales();
        cuentaService = new CuentaService(cuentaDAO);
        transaccionService = new TransaccionService(transaccionDAO);
        exchangeService = new ExchangeService(clienteService, cuentaService, transaccionService);
    }

    @Test
    void debeDepositarCorrectamente() throws Exception {
        exchangeService.depositar("20-11111111-3", "CA-001", 500);

        Cuenta cuenta = cuentaService.buscarPorNumero("CA-001");
        assertEquals(1500, cuenta.getSaldo(), 0.001);
        assertEquals(1, transaccionService.obtenerHistorial().size());
    }

    @Test
    void debeExtraerCorrectamente() throws Exception {
        exchangeService.extraer("20-11111111-3", "CA-001", 400);

        Cuenta cuenta = cuentaService.buscarPorNumero("CA-001");
        assertEquals(600, cuenta.getSaldo(), 0.001);
        assertEquals(1, transaccionService.obtenerHistorial().size());
    }

    @Test
    void debeTransferirCorrectamente() throws Exception {
        exchangeService.transferir("20-11111111-3", "CA-001", "CC-001", 300);

        Cuenta origen = cuentaService.buscarPorNumero("CA-001");
        Cuenta destino = cuentaService.buscarPorNumero("CC-001");

        assertEquals(700, origen.getSaldo(), 0.001);
        assertEquals(2300, destino.getSaldo(), 0.001);
        assertEquals(1, transaccionService.obtenerHistorial().size());
    }

    @Test
    void debeConvertirCorrectamente() throws Exception {
        exchangeService.convertir("20-11111111-3", "BTC-001", "ETH-001", 1, 10);

        Cuenta origen = cuentaService.buscarPorNumero("BTC-001");
        Cuenta destino = cuentaService.buscarPorNumero("ETH-001");

        assertEquals(1, origen.getSaldo(), 0.001);
        assertEquals(10, destino.getSaldo(), 0.001);
        assertEquals(1, transaccionService.obtenerHistorial().size());
    }

    private static class ClienteDAOMemory implements ClienteDAO {

        private final List<Cliente> clientes = new ArrayList<>();

        @Override
        public boolean guardar(Cliente cliente) {
            clientes.add(cliente);
            return true;
        }

        @Override
        public boolean actualizar(Cliente cliente) {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getCuit().equals(cliente.getCuit())) {
                    clientes.set(i, cliente);
                    return true;
                }
            }
            return false;
        }

        @Override
        public List<Cliente> obtenerTodos() {
            return new ArrayList<>(clientes);
        }

        @Override
        public Optional<Cliente> buscarPorCuit(String cuit) {
            return clientes.stream()
                    .filter(cliente -> cliente.getCuit().equals(cuit))
                    .findFirst();
        }
    }

    private static class CuentaDAOMemory implements CuentaDAO {

        private final List<Cuenta> cuentas = new ArrayList<>();

        @Override
        public boolean guardar(Cuenta cuenta) {
            cuentas.add(cuenta);
            return true;
        }

        @Override
        public boolean actualizar(Cuenta cuenta) {
            for (int i = 0; i < cuentas.size(); i++) {
                if (cuentas.get(i).getNumero().equals(cuenta.getNumero())) {
                    cuentas.set(i, cuenta);
                    return true;
                }
            }
            return false;
        }

        @Override
        public List<Cuenta> obtenerTodas() {
            return new ArrayList<>(cuentas);
        }

        @Override
        public Optional<Cuenta> buscarPorNumero(String numero) {
            return cuentas.stream()
                    .filter(cuenta -> cuenta.getNumero().equals(numero))
                    .findFirst();
        }
    }

    private static class TransaccionDAOMemory implements TransaccionDAO {

        private final List<Transaccion> transacciones = new ArrayList<>();

        @Override
        public boolean guardar(Transaccion transaccion) {
            transacciones.add(transaccion);
            return true;
        }

        @Override
        public List<Transaccion> obtenerTodas() {
            return new ArrayList<>(transacciones);
        }
    }
}
