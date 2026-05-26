package edu.usal.view.panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelClientes extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCuit;
    private JTextField txtTelefono;
    private JTextField txtCelular;
    private JTextField txtEmail;
    private JTextField txtDomicilio;
    private JButton btnGuardar;
    private JButton btnConsultarCuentas;
    private JTable tablaClientes;
    private JTable tablaCuentas;

    /**
     * Create the panel.
     */
    public PanelClientes() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 5));

        panelFormulario.add(new JLabel("Nombre"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("CUIT"));
        txtCuit = new JTextField();
        panelFormulario.add(txtCuit);

        panelFormulario.add(new JLabel("Telefono"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Celular"));
        txtCelular = new JTextField();
        panelFormulario.add(txtCelular);

        panelFormulario.add(new JLabel("Email"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Domicilio"));
        txtDomicilio = new JTextField();
        panelFormulario.add(txtDomicilio);

        btnGuardar = new JButton("Guardar cliente");
        panelFormulario.add(btnGuardar);

        btnConsultarCuentas = new JButton("Consultar cuentas");
        panelFormulario.add(btnConsultarCuentas);

        add(panelFormulario, BorderLayout.NORTH);

        tablaClientes = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre", "Apellido", "CUIT", "Email"}
        ));

        tablaCuentas = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Numero", "Tipo", "Saldo", "CBU / Direccion"}
        ));

        JPanel panelTablas = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTablas.add(new JScrollPane(tablaClientes));
        panelTablas.add(new JScrollPane(tablaCuentas));

        add(panelTablas, BorderLayout.CENTER);
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtCuit() {
        return txtCuit;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtCelular() {
        return txtCelular;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtDomicilio() {
        return txtDomicilio;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnConsultarCuentas() {
        return btnConsultarCuentas;
    }

    public JTable getTablaClientes() {
        return tablaClientes;
    }

    public JTable getTablaCuentas() {
        return tablaCuentas;
    }
}
