package edu.usal.view.panel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class PanelOperaciones extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> cmbOperacion;
    private JLabel lblCuentaOrigen;
    private JLabel lblCuentaDestino;
    private JLabel lblCotizacion;
    private JTextField txtCuit;
    private JTextField txtCuentaOrigen;
    private JTextField txtCuentaDestino;
    private JTextField txtMonto;
    private JTextField txtCotizacion;
    private JButton btnEjecutar;

    /**
     * Create the panel.
     */
    public PanelOperaciones() {
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel("Operacion"));
        cmbOperacion = new JComboBox<>(new String[]{"Deposito", "Extraccion", "Transferencia", "Conversion"});
        add(cmbOperacion);

        add(new JLabel("CUIT cliente"));
        txtCuit = new JTextField();
        add(txtCuit);

        lblCuentaOrigen = new JLabel("Cuenta origen");
        add(lblCuentaOrigen);
        txtCuentaOrigen = new JTextField();
        add(txtCuentaOrigen);

        lblCuentaDestino = new JLabel("Cuenta destino");
        add(lblCuentaDestino);
        txtCuentaDestino = new JTextField();
        add(txtCuentaDestino);

        add(new JLabel("Monto"));
        txtMonto = new JTextField();
        add(txtMonto);

        lblCotizacion = new JLabel("Cotizacion");
        add(lblCotizacion);
        txtCotizacion = new JTextField();
        add(txtCotizacion);

        btnEjecutar = new JButton("Ejecutar operacion");
        add(btnEjecutar);
        add(new JLabel(""));
    }

    public JComboBox<String> getCmbOperacion() {
        return cmbOperacion;
    }

    public JTextField getTxtCuit() {
        return txtCuit;
    }

    public JTextField getTxtCuentaOrigen() {
        return txtCuentaOrigen;
    }

    public JTextField getTxtCuentaDestino() {
        return txtCuentaDestino;
    }

    public JTextField getTxtMonto() {
        return txtMonto;
    }

    public JTextField getTxtCotizacion() {
        return txtCotizacion;
    }

    public JButton getBtnEjecutar() {
        return btnEjecutar;
    }

    public JLabel getLblCuentaOrigen() {
        return lblCuentaOrigen;
    }

    public JLabel getLblCuentaDestino() {
        return lblCuentaDestino;
    }

    public JLabel getLblCotizacion() {
        return lblCotizacion;
    }
}
