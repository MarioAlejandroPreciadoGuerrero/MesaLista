/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Control.ControlOperaciones;
import DTO.ReservacionDTO;
import Facade.ReservacionesFacade;
import Interface.IReservacionesFacade;
import Utils.Navegador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author USER
 */
public class PreferenciasReservacion extends JFrame {
    
    private final String usuarioId;
    private final String idRestauranteSeleccionado;
    private final String platilloSeleccionado;

    private int personas = 1;
    private final Double costoCalculado = 500.0;

    private JSpinner spinnerFecha;
    private JTextField txtArea;
    private JLabel lblPersonas;

    public PreferenciasReservacion(String usuarioId, String idRestaurante, String platilloSeleccionado) {
        this.usuarioId = usuarioId;
        this.idRestauranteSeleccionado = idRestaurante;
        this.platilloSeleccionado = platilloSeleccionado;

        setTitle("Preferencias de reservación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
        header.setBackground(new Color(255, 153, 51));

        JLabel lblTitulo = new JLabel("Preferencias de reservación:");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        header.add(lblTitulo);

        return header;
    }

    private JPanel crearCuerpo() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(new Color(245, 245, 245));
        body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        body.add(new JLabel("Platillo seleccionado: " + platilloSeleccionado));
        body.add(Box.createVerticalStrut(20));

        body.add(new JLabel("Fecha y hora:"));
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy hh:mm a");
        spinnerFecha.setEditor(timeEditor);
        spinnerFecha.setValue(new Date());
        body.add(spinnerFecha);
        body.add(Box.createVerticalStrut(20));

        body.add(new JLabel("Número de personas:"));
        lblPersonas = new JLabel(String.valueOf(personas));
        JPanel panelPersonas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPersonas.setOpaque(false);
        JButton btnMenos = new JButton("-");
        JButton btnMas = new JButton("+");

        btnMenos.addActionListener(e -> {
            if (personas > 1) {
                personas--;
                lblPersonas.setText(String.valueOf(personas));
            }
        });
        btnMas.addActionListener(e -> {
            personas++;
            lblPersonas.setText(String.valueOf(personas));
        });

        panelPersonas.add(btnMenos);
        panelPersonas.add(lblPersonas);
        panelPersonas.add(btnMas);
        body.add(panelPersonas);
        body.add(Box.createVerticalStrut(20));

        body.add(new JLabel("Área (Ej. Terraza, Interior):"));
        txtArea = new JTextField();
        body.add(txtArea);
        body.add(Box.createVerticalStrut(40));

        JButton btnConfirmar = new JButton("Confirmar reservación");
        btnConfirmar.setBackground(new Color(255, 153, 51));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.addActionListener(this::onConfirmarClick);

        body.add(btnConfirmar);

        return body;
    }

    private void onConfirmarClick(ActionEvent e) {
        try {
            Date fechaSeleccionada = (Date) spinnerFecha.getValue();
            LocalDateTime fechaLocal = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            String areaSeleccionada = txtArea.getText().trim();

            ReservacionDTO nuevaReservacion = new ReservacionDTO();
            nuevaReservacion.setRestauranteId(this.idRestauranteSeleccionado);
            nuevaReservacion.setUsuarioId(this.usuarioId);
            nuevaReservacion.setNumPersonas(this.personas);
            nuevaReservacion.setFechaHora(fechaLocal);
            nuevaReservacion.setAreaNombre(areaSeleccionada.isEmpty() ? "Sin especificar" : areaSeleccionada);
            nuevaReservacion.setCosto(this.costoCalculado);
            nuevaReservacion.setFolio("FOLIO-" + System.currentTimeMillis());

            ControlOperaciones.getInstancia().procesarReservaConPago(nuevaReservacion);

            JOptionPane.showMessageDialog(this, "¡Reservación confirmada exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            Navegador navegador = new Navegador();
            navegador.ir(this, new ListaRestaurantes(this.usuarioId));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
