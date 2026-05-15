package Pantallas;

import Entidades.Area;
import Entidades.Platillo;
import Entidades.Reservacion;
import Entidades.Restaurante;
import Entidades.Usuario;
import Repository.ReservacionRepository;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Pantalla 4: Preferencias de la reservación.
 * Permite escoger fecha/hora, número de personas y confirmar.
 * Al confirmar guarda la Reservacion en MongoDB vía ReservacionRepository.
 */
public class PantallaPreferenciasReservacion extends JFrame {

    private static final Color  NARANJA     = new Color(249, 115, 22);
    private static final Color  FONDO       = new Color(250, 249, 246);
    private static final double COSTO_BASE  = 150.0;

    private final Usuario           usuarioActual;
    private final Restaurante       restaurante;
    private final Platillo          platilloPrincipal;
    private final List<Platillo>    extrasSeleccionados;
    private final Area              areaSeleccionada;
    private final List<Restaurante> restaurantes; // para navegar a MisReservaciones

    private int    personas = 1;
    private JLabel lblPersonas;
    private JLabel lblCosto;
    private JLabel lblFecha;

    public PantallaPreferenciasReservacion(Usuario usuario, Restaurante restaurante,
                                           Platillo platillo, List<Platillo> extras,
                                           Area area, List<Restaurante> restaurantes) {
        this.usuarioActual       = usuario;
        this.restaurante         = restaurante;
        this.platilloPrincipal   = platillo;
        this.extrasSeleccionados = extras;
        this.areaSeleccionada    = area;
        this.restaurantes        = restaurantes;

        setTitle("Preferencias de reservación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);

        setSize(700, 480);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel lbl = new JLabel("Preferencias de reservación:");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setForeground(Color.WHITE);
        header.add(lbl);
        return header;
    }

    private JPanel crearCuerpo() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(22, 28, 26, 28));

        body.add(fila("Restaurante :  " + restaurante.getNombre()));
        body.add(Box.createVerticalStrut(14));
        body.add(crearFilaFecha());
        body.add(Box.createVerticalStrut(14));
        body.add(crearFilaPersonasCosto());
        body.add(Box.createVerticalStrut(14));
        body.add(crearFilaArea());
        body.add(Box.createVerticalStrut(22));

        JPanel centerSig = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerSig.setOpaque(false);
        centerSig.add(crearBoton("Siguiente", e -> onSiguiente()));
        body.add(centerSig);

        body.add(Box.createVerticalStrut(14));
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(229, 224, 216));
        body.add(sep);
        body.add(Box.createVerticalStrut(14));
        body.add(crearFilaOtraPersona());

        return body;
    }

    private JPanel fila(String texto) {
        JPanel f = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        f.setOpaque(false);
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        f.add(lbl);
        return f;
    }

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
                lblFecha.setText(new SimpleDateFormat("dd/MM/yyyy hh:mma").format((Date) spinner.getValue()));
        });

        fila.add(new JLabel("Fecha y hora:"));
        fila.add(lblFecha);
        fila.add(btnCal);
        return fila;
    }

    private JPanel crearFilaPersonasCosto() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.setOpaque(false);

        lblPersonas = new JLabel("1");
        lblPersonas.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblCosto = new JLabel(calcularCosto());
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
        fila.add(new JLabel("Costo :"));
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
        lblCosto.setText(calcularCosto());
    }

    private String calcularCosto() {
        return String.format("$%.2f", calcularCostoDouble());
    }

    private double calcularCostoDouble() {
        double total = COSTO_BASE * personas;
        if (platilloPrincipal.getPrecio() != null) total += platilloPrincipal.getPrecio();
        for (Platillo ex : extrasSeleccionados) {
            if (ex.getPrecio() != null) total += ex.getPrecio();
        }
        return total;
    }

    private JPanel crearFilaArea() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila.setOpaque(false);
        String area = areaSeleccionada != null ? areaSeleccionada.getNombre() : "Sin área seleccionada";
        JLabel lbl = new JLabel(area);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        fila.add(new JLabel("Área:"));
        fila.add(lbl);
        return fila;
    }

    private JPanel crearFilaOtraPersona() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        fila.setOpaque(false);
        JLabel lbl = new JLabel("Desea reservar para otra persona");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        fila.add(lbl);
        fila.add(crearBoton("Reservar", e ->
            JOptionPane.showMessageDialog(this,
                "Funcionalidad para reservar a nombre de otra persona próximamente.")));
        return fila;
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(140, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(accion);
        return btn;
    }

    private void onSiguiente() {
        String areaNombre = areaSeleccionada != null ? areaSeleccionada.getNombre() : "";

        String resumen = String.format(
            "Restaurante: %s%nPlatillo: %s%nFecha: %s%nPersonas: %d%nCosto: %s%nÁrea: %s",
            restaurante.getNombre(),
            platilloPrincipal.getNombrePlatillo(),
            lblFecha.getText(),
            personas,
            calcularCosto(),
            areaNombre.isEmpty() ? "Sin especificar" : areaNombre
        );

        int op = JOptionPane.showConfirmDialog(this, resumen,
            "Confirmar reservación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (op == JOptionPane.OK_OPTION) {
            try {
                Date fechaDate = new SimpleDateFormat("dd/MM/yyyy hh:mma").parse(lblFecha.getText());
                LocalDateTime fechaHora = fechaDate.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();

                Reservacion res = new Reservacion();
                res.setNumPersonas(personas);
                res.setFolio("ML-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                res.setCosto(calcularCostoDouble());
                res.setFechaHora(fechaHora);
                res.setUsuarioId(usuarioActual.getId());
                res.setRestauranteId(restaurante.getId());
                res.setAreaNombre(areaNombre);

                ReservacionRepository repo = new ReservacionRepository();
                repo.guardar(res);

                JOptionPane.showMessageDialog(this,
                    "¡Reservación confirmada!\nFolio: " + res.getFolio(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

                Navegador.ir(new PantallaMisReservaciones(usuarioActual, restaurantes));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar la reservación:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
