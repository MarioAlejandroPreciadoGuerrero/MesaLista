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

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private final String usuarioId;
    private final String nombreUsuario;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;

    public HistorialReservaciones(String usuarioId, String nombreUsuario) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        
        setTitle("MesaLista - Mi Historial de Reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(FONDO);

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JLabel lblTitulo = new JLabel("🍽  Mis Reservaciones Pasadas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(lblTitulo, BorderLayout.WEST);

        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnVolver.setBackground(Color.WHITE);
        btnVolver.setForeground(NARANJA);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> new Navegador().ir(this, new ListaRestaurantes(usuarioId, nombreUsuario)));
        header.add(btnVolver, BorderLayout.EAST);

        return header;
    }

    private JPanel crearCuerpo() {
        JPanel panelCuerpo = new JPanel(new BorderLayout());
        panelCuerpo.setOpaque(false);
        panelCuerpo.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        String[] columnas = {"Folio", "Fecha y Hora", "Área", "Comensales", "Costo ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaHistorial.setRowHeight(24);
        tablaHistorial.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        panelCuerpo.add(scrollPane, BorderLayout.CENTER);

        cargarDatos();
        return panelCuerpo;
    }

    private void cargarDatos() {
        try {
            List<ReservacionDTO> historial = ControlOperaciones.getInstancia().obtenerHistorialReservaciones(this.usuarioId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            if (historial == null || historial.isEmpty()) {
                return;
            }

            for (ReservacionDTO res : historial) {
                Object[] fila = {
                    res.getFolio(),
                    res.getFechaHora() != null ? res.getFechaHora().format(formatter) : "N/A",
                    res.getAreaNombre(),
                    res.getNumPersonas(),
                    "$" + res.getCosto()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar historial: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
 