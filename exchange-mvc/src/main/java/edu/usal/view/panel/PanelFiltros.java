package edu.usal.view.panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;

public class PanelFiltros extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtMontoMinimo;
    private JButton btnFiltroFiat;
    private JTable tablaFiltroFiat;

    private JTextField txtFechaDesde;
    private JTextField txtFechaHasta;
    private JButton btnFiltroBtc;
    private JTable tablaFiltroBtc;

    /**
     * Create the panel.
     */
    public PanelFiltros() {
        setLayout(new GridLayout(2, 1, 10, 10));

        JPanel panelFiat = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel panelFiatCampos = new JPanel(new GridLayout(1, 3, 5, 5));
        txtMontoMinimo = new JTextField();
        btnFiltroFiat = new JButton("Filtrar Fiat");
        panelFiatCampos.add(txtMontoMinimo);
        panelFiatCampos.add(btnFiltroFiat);
        panelFiatCampos.add(new JLabel("Ingrese monto minimo"));
        panelFiat.add(panelFiatCampos);

        tablaFiltroFiat = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"CUIT Origen", "CBU Origen", "CUIT Destino", "CBU Destino", "Monto", "Fecha"}
        ));
        panelFiat.add(new JScrollPane(tablaFiltroFiat));

        JPanel panelBtc = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel panelBtcCampos = new JPanel(new GridLayout(1, 4, 5, 5));
        txtFechaDesde = new JTextField();
        txtFechaHasta = new JTextField();
        btnFiltroBtc = new JButton("Filtrar BTC");
        panelBtcCampos.add(txtFechaDesde);
        panelBtcCampos.add(txtFechaHasta);
        panelBtcCampos.add(btnFiltroBtc);
        panelBtcCampos.add(new JLabel("Formato yyyy-MM-dd"));
        panelBtc.add(panelBtcCampos);

        tablaFiltroBtc = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Direccion Origen", "Direccion Destino", "Monto", "Fecha"}
        ));
        panelBtc.add(new JScrollPane(tablaFiltroBtc));

        add(panelFiat);
        add(panelBtc);
    }

    public JTextField getTxtMontoMinimo() {
        return txtMontoMinimo;
    }

    public JButton getBtnFiltroFiat() {
        return btnFiltroFiat;
    }

    public JTable getTablaFiltroFiat() {
        return tablaFiltroFiat;
    }

    public JTextField getTxtFechaDesde() {
        return txtFechaDesde;
    }

    public JTextField getTxtFechaHasta() {
        return txtFechaHasta;
    }

    public JButton getBtnFiltroBtc() {
        return btnFiltroBtc;
    }

    public JTable getTablaFiltroBtc() {
        return tablaFiltroBtc;
    }
}
