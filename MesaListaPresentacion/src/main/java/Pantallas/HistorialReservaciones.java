/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Control.ControlOperaciones;
import DTO.ReservacionDTO;
import Utils.Navegador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * @author JOSE ANTONIO GONZALEZ VALLE
 */
public class HistorialReservaciones extends JFrame {

    private final String usuarioId;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;

    public HistorialReservaciones(String usuarioId) {
        this.usuarioId = usuarioId;
        configurarVentana();
        agregarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("MesaLista - Mi Historial de Reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void agregarComponentes() {
        // Encabezado
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 153, 51));
        JLabel lblTitulo = new JLabel("Mis Reservaciones Pasadas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(lblTitulo);
        add(header, BorderLayout.NORTH);

        // Tabla de datos
        String[] columnas = {"Folio", "Fecha y Hora", "Área", "Comensales", "Costo ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquear edición de celdas
            }
        };
        
        tablaHistorial = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Botón Volver (Pie de página)
        JPanel footer = new JPanel();
        JButton btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.addActionListener(e -> new Navegador().ir(this, new ListaRestaurantes(this.usuarioId)));
        footer.add(btnVolver);
        add(footer, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        try {
            List<ReservacionDTO> historial = ControlOperaciones.getInstancia().obtenerHistorialReservaciones(this.usuarioId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            if (historial == null || historial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aún no tienes reservaciones registradas.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (ReservacionDTO res : historial) {
                Object[] fila = {
                    res.getFolio(),
                    res.getFechaHora() != null ? res.getFechaHora().format(formatter) : "N/A",
                    res.getAreaNombre(),
                    res.getNumPersonas(),
                    res.getCosto()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar historial: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}