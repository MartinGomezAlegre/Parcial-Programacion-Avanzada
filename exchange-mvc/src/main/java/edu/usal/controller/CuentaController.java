package edu.usal.controller;

import edu.usal.service.ExchangeService;
import edu.usal.view.panel.PanelOperaciones;

import javax.swing.JOptionPane;

public class CuentaController {

    private final ExchangeService exchangeService;
    private final PanelOperaciones vista;
    private final TransaccionController transaccionController;

    public CuentaController(ExchangeService exchangeService, PanelOperaciones vista, TransaccionController transaccionController) {
        this.exchangeService = exchangeService;
        this.vista = vista;
        this.transaccionController = transaccionController;

        this.vista.getBtnEjecutar().addActionListener(e -> ejecutarOperacion());
        this.vista.getCmbOperacion().addActionListener(e -> actualizarCamposSegunOperacion());
        actualizarCamposSegunOperacion();
    }

    private void ejecutarOperacion() {
        try {
            String operacion = (String) vista.getCmbOperacion().getSelectedItem();
            String cuit = vista.getTxtCuit().getText();
            String cuentaOrigen = vista.getTxtCuentaOrigen().getText();
            String cuentaDestino = vista.getTxtCuentaDestino().getText();
            double monto = Double.parseDouble(vista.getTxtMonto().getText());

            if ("Deposito".equalsIgnoreCase(operacion)) {
                exchangeService.depositar(cuit, cuentaDestino, monto);
            } else if ("Extraccion".equalsIgnoreCase(operacion)) {
                exchangeService.extraer(cuit, cuentaOrigen, monto);
            } else if ("Transferencia".equalsIgnoreCase(operacion)) {
                exchangeService.transferir(cuit, cuentaOrigen, cuentaDestino, monto);
            } else if ("Conversion".equalsIgnoreCase(operacion)) {
                double cotizacion = Double.parseDouble(vista.getTxtCotizacion().getText());
                exchangeService.convertir(cuit, cuentaOrigen, cuentaDestino, monto, cotizacion);
            }

            transaccionController.refrescarHistorial();
            limpiarFormulario();
            JOptionPane.showMessageDialog(vista, "Operacion realizada correctamente");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        vista.getTxtCuit().setText("");
        vista.getTxtCuentaOrigen().setText("");
        vista.getTxtCuentaDestino().setText("");
        vista.getTxtMonto().setText("");
        vista.getTxtCotizacion().setText("");
    }

    private void actualizarCamposSegunOperacion() {
        String operacion = (String) vista.getCmbOperacion().getSelectedItem();

        if ("Deposito".equalsIgnoreCase(operacion)) {
            vista.getLblCuentaOrigen().setText("Cuenta origen");
            vista.getLblCuentaDestino().setText("Cuenta destino");
            vista.getTxtCuentaOrigen().setEnabled(false);
            vista.getTxtCuentaOrigen().setText("");
            vista.getTxtCuentaDestino().setEnabled(true);
            vista.getTxtCotizacion().setEnabled(false);
            vista.getTxtCotizacion().setText("");
            return;
        }

        if ("Extraccion".equalsIgnoreCase(operacion)) {
            vista.getTxtCuentaOrigen().setEnabled(true);
            vista.getTxtCuentaDestino().setEnabled(false);
            vista.getTxtCuentaDestino().setText("");
            vista.getTxtCotizacion().setEnabled(false);
            vista.getTxtCotizacion().setText("");
            return;
        }

        if ("Transferencia".equalsIgnoreCase(operacion)) {
            vista.getTxtCuentaOrigen().setEnabled(true);
            vista.getTxtCuentaDestino().setEnabled(true);
            vista.getTxtCotizacion().setEnabled(false);
            vista.getTxtCotizacion().setText("");
            return;
        }

        vista.getTxtCuentaOrigen().setEnabled(true);
        vista.getTxtCuentaDestino().setEnabled(true);
        vista.getTxtCotizacion().setEnabled(true);
    }
}
