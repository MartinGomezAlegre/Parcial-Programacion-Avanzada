package edu.usal.controller;

import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.exceptions.ClienteNoEncontradoException;
import edu.usal.modelo.clientes.Cliente;
import edu.usal.modelo.clientes.DatosContacto;
import edu.usal.modelo.cuentas.Cuenta;
import edu.usal.modelo.cuentas.CuentaBancaria;
import edu.usal.modelo.cuentas.WalletCrypto;
import edu.usal.service.ClienteService;
import edu.usal.service.CuentaService;
import edu.usal.view.panel.PanelClientes;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClienteController {

    private final ClienteService clienteService;
    private final CuentaService cuentaService;
    private final PanelClientes vista;

    public ClienteController(ClienteService clienteService, CuentaService cuentaService, PanelClientes vista) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.vista = vista;

        this.vista.getBtnGuardar().addActionListener(e -> guardarCliente());
        this.vista.getBtnConsultarCuentas().addActionListener(e -> consultarCuentasDelCliente());
        cargarTablaClientes();
    }

    private void guardarCliente() {
        try {
            DatosContacto datosContacto = new DatosContacto(
                    vista.getTxtTelefono().getText(),
                    vista.getTxtCelular().getText(),
                    vista.getTxtEmail().getText(),
                    vista.getTxtDomicilio().getText()
            );

            Cliente cliente = new Cliente(
                    vista.getTxtNombre().getText(),
                    vista.getTxtApellido().getText(),
                    vista.getTxtCuit().getText(),
                    datosContacto
            );

            clienteService.guardarCliente(cliente);
            cargarTablaClientes();
            limpiarFormulario();
            JOptionPane.showMessageDialog(vista, "Cliente guardado correctamente");
        } catch (ArchivoPersistenciaException ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTablaClientes() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaClientes().getModel();
        model.setRowCount(0);

        for (Cliente cliente : clienteService.obtenerClientesEnMemoria()) {
            model.addRow(new Object[]{
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getCuit(),
                    cliente.getDatosContacto() != null ? cliente.getDatosContacto().getEmail() : ""
            });
        }
    }

    private void consultarCuentasDelCliente() {
        try {
            Cliente cliente = clienteService.buscarPorCuit(vista.getTxtCuit().getText());
            List<Cuenta> cuentas = cuentaService.obtenerPorNumeros(cliente.getNumerosCuenta());

            DefaultTableModel model = (DefaultTableModel) vista.getTablaCuentas().getModel();
            model.setRowCount(0);

            for (Cuenta cuenta : cuentas) {
                model.addRow(crearFilaCuenta(cuenta));
            }
        } catch (ClienteNoEncontradoException | ArchivoPersistenciaException ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object[] crearFilaCuenta(Cuenta cuenta) {
        if (cuenta instanceof CuentaBancaria) {
            CuentaBancaria cuentaBancaria = (CuentaBancaria) cuenta;
            return new Object[]{
                    cuentaBancaria.getNumero(),
                    cuentaBancaria.getClass().getSimpleName(),
                    cuentaBancaria.getSaldo(),
                    cuentaBancaria.getCbu()
            };
        }

        if (cuenta instanceof WalletCrypto) {
            WalletCrypto walletCrypto = (WalletCrypto) cuenta;
            return new Object[]{
                    walletCrypto.getNumero(),
                    walletCrypto.getTipoCrypto(),
                    walletCrypto.getSaldo(),
                    walletCrypto.getDireccion()
            };
        }

        return new Object[]{
                cuenta.getNumero(),
                cuenta.getClass().getSimpleName(),
                cuenta.getSaldo(),
                ""
        };
    }

    private void limpiarFormulario() {
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtCuit().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtCelular().setText("");
        vista.getTxtEmail().setText("");
        vista.getTxtDomicilio().setText("");
    }
}
