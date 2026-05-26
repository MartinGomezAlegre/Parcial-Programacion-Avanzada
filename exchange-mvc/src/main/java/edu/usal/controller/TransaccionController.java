package edu.usal.controller;

import edu.usal.modelo.transacciones.Conversion;
import edu.usal.modelo.transacciones.Deposito;
import edu.usal.modelo.transacciones.Extraccion;
import edu.usal.modelo.transacciones.Transaccion;
import edu.usal.modelo.transacciones.Transferencia;
import edu.usal.service.ExchangeService;
import edu.usal.service.dto.ResultadoFiltroBtcDTO;
import edu.usal.service.dto.ResultadoFiltroFiatDTO;
import edu.usal.view.panel.PanelFiltros;
import edu.usal.view.panel.PanelHistorial;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;

public class TransaccionController {

    private final ExchangeService exchangeService;
    private final PanelHistorial panelHistorial;
    private final PanelFiltros panelFiltros;

    public TransaccionController(ExchangeService exchangeService, PanelHistorial panelHistorial, PanelFiltros panelFiltros) {
        this.exchangeService = exchangeService;
        this.panelHistorial = panelHistorial;
        this.panelFiltros = panelFiltros;

        this.panelHistorial.getBtnActualizar().addActionListener(e -> refrescarHistorial());
        this.panelFiltros.getBtnFiltroFiat().addActionListener(e -> aplicarFiltroFiat());
        this.panelFiltros.getBtnFiltroBtc().addActionListener(e -> aplicarFiltroBtc());
    }

    public void cargarDatosIniciales() {
        refrescarHistorial();
    }

    public void refrescarHistorial() {
        try {
            List<Transaccion> historial = exchangeService.getTransaccionService().obtenerHistorial();
            DefaultTableModel model = (DefaultTableModel) panelHistorial.getTablaHistorial().getModel();
            model.setRowCount(0);

            for (Transaccion transaccion : historial) {
                model.addRow(crearFilaHistorial(transaccion));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelHistorial, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aplicarFiltroFiat() {
        try {
            double montoMinimo = Double.parseDouble(panelFiltros.getTxtMontoMinimo().getText());
            List<ResultadoFiltroFiatDTO> resultados = exchangeService.getTransaccionService()
                    .filtrarTransaccionesFiatMayoresA(montoMinimo, exchangeService.getCuentaService());

            DefaultTableModel model = (DefaultTableModel) panelFiltros.getTablaFiltroFiat().getModel();
            model.setRowCount(0);

            for (ResultadoFiltroFiatDTO resultado : resultados) {
                model.addRow(new Object[]{
                        resultado.getCuitOrigen(),
                        resultado.getCbuOrigen(),
                        resultado.getCuitDestino(),
                        resultado.getCbuDestino(),
                        resultado.getMonto(),
                        resultado.getFecha()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelFiltros, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aplicarFiltroBtc() {
        try {
            LocalDate fechaDesde = LocalDate.parse(panelFiltros.getTxtFechaDesde().getText());
            LocalDate fechaHasta = LocalDate.parse(panelFiltros.getTxtFechaHasta().getText());

            List<ResultadoFiltroBtcDTO> resultados = exchangeService.getTransaccionService()
                    .filtrarTransaccionesBtcPorFechas(fechaDesde, fechaHasta, exchangeService.getCuentaService());

            DefaultTableModel model = (DefaultTableModel) panelFiltros.getTablaFiltroBtc().getModel();
            model.setRowCount(0);

            for (ResultadoFiltroBtcDTO resultado : resultados) {
                model.addRow(new Object[]{
                        resultado.getDireccionOrigen(),
                        resultado.getDireccionDestino(),
                        resultado.getMonto(),
                        resultado.getFecha()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelFiltros, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object[] crearFilaHistorial(Transaccion transaccion) {
        if (transaccion instanceof Deposito) {
            Deposito deposito = (Deposito) transaccion;
            return new Object[]{"Deposito", deposito.getCuitCliente(), deposito.getFecha(), deposito.getMonto(), "", deposito.getNumeroCuentaDestino()};
        }
        if (transaccion instanceof Extraccion) {
            Extraccion extraccion = (Extraccion) transaccion;
            return new Object[]{"Extraccion", extraccion.getCuitCliente(), extraccion.getFecha(), extraccion.getMonto(), extraccion.getNumeroCuentaOrigen(), ""};
        }
        if (transaccion instanceof Transferencia) {
            Transferencia transferencia = (Transferencia) transaccion;
            return new Object[]{"Transferencia", transferencia.getCuitCliente(), transferencia.getFecha(), transferencia.getMonto(), transferencia.getNumeroCuentaOrigen(), transferencia.getNumeroCuentaDestino()};
        }
        if (transaccion instanceof Conversion) {
            Conversion conversion = (Conversion) transaccion;
            return new Object[]{"Conversion", conversion.getCuitCliente(), conversion.getFecha(), conversion.getMonto(), conversion.getNumeroCuentaOrigen(), conversion.getNumeroCuentaDestino()};
        }
        return new Object[]{"", transaccion.getCuitCliente(), transaccion.getFecha(), transaccion.getMonto(), "", ""};
    }
}
