package edu.usal.view.frame;

import edu.usal.view.panel.PanelClientes;
import edu.usal.view.panel.PanelFiltros;
import edu.usal.view.panel.PanelHistorial;
import edu.usal.view.panel.PanelOperaciones;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private PanelClientes panelClientes;
    private PanelOperaciones panelOperaciones;
    private PanelHistorial panelHistorial;
    private PanelFiltros panelFiltros;

    /**
     * Create the frame.
     */
    public VentanaPrincipal() {
        setTitle("Exchange Crypto - USAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JTabbedPane tabbedPane = new JTabbedPane();
        panelClientes = new PanelClientes();
        panelOperaciones = new PanelOperaciones();
        panelHistorial = new PanelHistorial();
        panelFiltros = new PanelFiltros();

        tabbedPane.addTab("Clientes", panelClientes);
        tabbedPane.addTab("Operaciones", panelOperaciones);
        tabbedPane.addTab("Historial", panelHistorial);
        tabbedPane.addTab("Filtros", panelFiltros);

        contentPane.add(tabbedPane, BorderLayout.CENTER);
    }

    public PanelClientes getPanelClientes() {
        return panelClientes;
    }

    public PanelOperaciones getPanelOperaciones() {
        return panelOperaciones;
    }

    public PanelHistorial getPanelHistorial() {
        return panelHistorial;
    }

    public PanelFiltros getPanelFiltros() {
        return panelFiltros;
    }
}
