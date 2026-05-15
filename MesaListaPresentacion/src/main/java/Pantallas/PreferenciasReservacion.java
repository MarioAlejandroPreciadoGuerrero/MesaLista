package Pantallas;

import Control.ControlOperaciones;
import DTO.ReservacionDTO;
import Utils.Navegador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PreferenciasReservacion extends JFrame {

    private static final Color NARANJA    = new Color(249, 115, 22);
    private static final Color FONDO      = new Color(250, 249, 246);
    private static final double COSTO_BASE = 150.0;

    private final String usuarioId;
    private final String nombreUsuario;
    private final String restauranteId;
    private final String platilloSeleccionado;
    private final Double precioPlatillo;

    private int    personas = 1;
    private JLabel lblPersonas;
    private JLabel lblCosto;
    private JLabel lblFecha;

    public PreferenciasReservacion(String usuarioId, String nombreUsuario,
                                   String restauranteId, String platillo, Double precio) {
        this.usuarioId          = usuarioId;
        this.nombreUsuario      = nombreUsuario;
        this.restauranteId      = restauranteId;
        this.platilloSeleccionado = platillo;
        this.precioPlatillo     = precio != null ? precio : 0.0;

        setTitle("MesaLista - Preferencias de Reservación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
    }

    // ── Header ──────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JLabel titulo = new JLabel("Preferencias de reservación:");
        titulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        titulo.setForeground(Color.WHITE);

        JButton btnVolver = new JButton("‹ Volver");
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 1, true));
        btnVolver.setContentAreaFilled(false);
        btnVolver.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e ->
            new Navegador().ir(this, new ListaRestaurantes(usuarioId, nombreUsuario))
        );

        header.add(titulo,    BorderLayout.WEST);
        header.add(btnVolver, BorderLayout.EAST);
        return header;
    }

    // ── Cuerpo principal ────────────────────────────────────────────────────
    private JPanel crearCuerpo() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(24, 32, 28, 32));

        // Fila: platillo seleccionado
        body.add(filaInfo("Platillo:  " + platilloSeleccionado
            + "  ($" + String.format("%.2f", precioPlatillo) + " c/u)"));
        body.add(Box.createVerticalStrut(16));

        // Fila: fecha y hora
        body.add(crearFilaFecha());
        body.add(Box.createVerticalStrut(16));

        // Fila: personas + costo
        body.add(crearFilaPersonasCosto());
        body.add(Box.createVerticalStrut(16));

        // Fila: área (texto libre)
        body.add(crearFilaArea());
        body.add(Box.createVerticalStrut(24));

        // Botón confirmar centrado
        JPanel centerBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerBtn.setOpaque(false);
        centerBtn.add(crearBoton("Confirmar reservación", this::onConfirmarClick));
        body.add(centerBtn);

        body.add(Box.createVerticalStrut(16));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(229, 224, 216));
        body.add(sep);
        body.add(Box.createVerticalStrut(14));

        // Fila: reservar para otra persona
        body.add(crearFilaOtraPersona());
        return body;
    }

    private JPanel filaInfo(String texto) {
        JPanel f = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        f.setOpaque(false);
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        f.add(lbl);
        return f;
    }

    // ── Fecha y hora ────────────────────────────────────────────────────────
    private JPanel crearFilaFecha() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.setOpaque(false);

        lblFecha = new JLabel(new SimpleDateFormat("dd/MM/yyyy hh:mma").format(new Date()));
        lblFecha.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JButton btnCal = new JButton("📅");
        btnCal.setBorderPainted(false);
        btnCal.setContentAreaFilled(false);
        btnCal.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnCal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCal.addActionListener(e -> {
            JSpinner spinner = new JSpinner(new SpinnerDateModel());
            spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy hh:mma"));
            spinner.setValue(new Date());
            int r = JOptionPane.showConfirmDialog(this, spinner,
                "Selecciona fecha y hora", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (r == JOptionPane.OK_OPTION)
                lblFecha.setText(new SimpleDateFormat("dd/MM/yyyy hh:mma")
                    .format((Date) spinner.getValue()));
        });

        fila.add(new JLabel("Fecha y hora:"));
        fila.add(lblFecha);
        fila.add(btnCal);
        return fila;
    }

    // ── Número de personas + costo ──────────────────────────────────────────
    private JPanel crearFilaPersonasCosto() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.setOpaque(false);

        lblPersonas = new JLabel("1");
        lblPersonas.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblCosto = new JLabel(calcularCostoStr());
        lblCosto.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JButton menos = crearBtnContador("−");
        JButton mas   = crearBtnContador("+");
        menos.addActionListener(e -> cambiarPersonas(-1));
        mas.addActionListener(e   -> cambiarPersonas(1));

        fila.add(new JLabel("Número de personas:"));
        fila.add(menos);
        fila.add(lblPersonas);
        fila.add(mas);
        fila.add(Box.createHorizontalStrut(16));
        fila.add(new JLabel("Costo:"));
        fila.add(lblCosto);
        return fila;
    }

    private JButton crearBtnContador(String txt) {
        JButton btn = new JButton(txt);
        btn.setPreferredSize(new Dimension(30, 28));
        btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void cambiarPersonas(int delta) {
        personas = Math.max(1, personas + delta);
        lblPersonas.setText(String.valueOf(personas));
        lblCosto.setText(calcularCostoStr());
    }

    private double calcularCosto() {
        return COSTO_BASE * personas + precioPlatillo;
    }

    private String calcularCostoStr() {
        return String.format("$%.2f", calcularCosto());
    }

    // ── Área ────────────────────────────────────────────────────────────────
    private JTextField txtArea;

    private JPanel crearFilaArea() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.setOpaque(false);

        txtArea = new JTextField(18);
        txtArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 204, 196), 1),
            BorderFactory.createEmptyBorder(3, 8, 3, 8)));
        txtArea.setBackground(Color.WHITE);

        fila.add(new JLabel("Área (Ej. Terraza, Interior):"));
        fila.add(txtArea);
        return fila;
    }

    // ── Reservar para otra persona ──────────────────────────────────────────
    private JPanel crearFilaOtraPersona() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        fila.setOpaque(false);
        JLabel lbl = new JLabel("¿Desea reservar para otra persona?");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fila.add(lbl);
        fila.add(crearBoton("Reservar", e ->
            JOptionPane.showMessageDialog(this,
                "Funcionalidad para reservar a nombre de otra persona próximamente.")));
        return fila;
    }

    // ── Confirmar ───────────────────────────────────────────────────────────
    private void onConfirmarClick(ActionEvent e) {
        try {
            Date fechaDate = new SimpleDateFormat("dd/MM/yyyy hh:mma").parse(lblFecha.getText());
            LocalDateTime fechaLocal = fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            String area = txtArea.getText().trim();

            ReservacionDTO nueva = new ReservacionDTO();
            nueva.setRestauranteId(restauranteId);
            nueva.setUsuarioId(usuarioId);
            nueva.setNumPersonas(personas);
            nueva.setFechaHora(fechaLocal);
            nueva.setAreaNombre(area.isEmpty() ? "Sin especificar" : area);
            nueva.setCosto(calcularCosto());
            nueva.setFolio("ML-" + Long.toHexString(System.currentTimeMillis()).toUpperCase());

            // Confirmación con resumen
            String resumen = String.format(
                "Platillo: %s%nFecha: %s%nPersonas: %d%nÁrea: %s%nTotal: %s",
                platilloSeleccionado, lblFecha.getText(), personas,
                nueva.getAreaNombre(), calcularCostoStr());

            int op = JOptionPane.showConfirmDialog(this, resumen,
                "Confirmar reservación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (op != JOptionPane.OK_OPTION) return;

            ControlOperaciones.getInstancia().procesarReservaConPago(nueva);

            JOptionPane.showMessageDialog(this,
                "¡Reservación confirmada!\nFolio: " + nueva.getFolio(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            new Navegador().ir(this, new ListaRestaurantes(usuarioId, nombreUsuario));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Helper botón ────────────────────────────────────────────────────────
    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(200, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(accion);
        return btn;
    }
}
