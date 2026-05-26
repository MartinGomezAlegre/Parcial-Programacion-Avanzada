package edu.usal.view.panel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class PanelHistorial extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tablaHistorial;
    private JButton btnActualizar;

    /**
     * Create the panel.
     */
    public PanelHistorial() {
        setLayout(new BorderLayout(10, 10));

        btnActualizar = new JButton("Actualizar historial");
        add(btnActualizar, BorderLayout.NORTH);

        tablaHistorial = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Tipo", "CUIT Cliente", "Fecha", "Monto", "Origen", "Destino"}
        ));

        add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
    }

    public JTable getTablaHistorial() {
        return tablaHistorial;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }
}
