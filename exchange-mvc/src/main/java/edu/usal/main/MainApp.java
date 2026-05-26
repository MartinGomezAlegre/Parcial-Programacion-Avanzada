package edu.usal.main;

import edu.usal.controller.ClienteController;
import edu.usal.controller.CuentaController;
import edu.usal.controller.TransaccionController;
import edu.usal.exceptions.ArchivoPersistenciaException;
import edu.usal.factory.ServiceFactory;
import edu.usal.service.ExchangeService;
import edu.usal.view.frame.VentanaPrincipal;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

public class MainApp {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ExchangeService exchangeService = ServiceFactory.crearExchangeService();
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

                TransaccionController transaccionController = new TransaccionController(
                        exchangeService,
                        ventanaPrincipal.getPanelHistorial(),
                        ventanaPrincipal.getPanelFiltros()
                );

                new ClienteController(
                        exchangeService.getClienteService(),
                        exchangeService.getCuentaService(),
                        ventanaPrincipal.getPanelClientes()
                );
                new CuentaController(exchangeService, ventanaPrincipal.getPanelOperaciones(), transaccionController);

                transaccionController.cargarDatosIniciales();
                ventanaPrincipal.setVisible(true);
            } catch (ArchivoPersistenciaException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "No se pudo iniciar la aplicacion: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
