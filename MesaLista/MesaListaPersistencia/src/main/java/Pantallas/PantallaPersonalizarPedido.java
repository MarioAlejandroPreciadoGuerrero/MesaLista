package Pantallas;

import Entidades.Area;
import Entidades.Platillo;
import Entidades.Restaurante;
import Entidades.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pantalla 3: Personalización del pedido.
 */
public class PantallaPersonalizarPedido extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private final Usuario           usuarioActual;
    private final Restaurante       restaurante;
    private final Platillo          platilloPrincipal;
    private final List<Restaurante> restaurantes; // para propagar

    private final List<JCheckBox>    checksPlatillos = new ArrayList<>();
    private final List<JRadioButton> radiosArea      = new ArrayList<>();

    public PantallaPersonalizarPedido(Usuario usuario, Restaurante restaurante,
                                      Platillo platillo, List<Restaurante> restaurantes) {
        this.usuarioActual    = usuario;
        this.restaurante      = restaurante;
        this.platilloPrincipal = platillo;
        this.restaurantes     = restaurantes;

        setTitle("Personalizar pedido - " + restaurante.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearHeader(),  BorderLayout.NORTH);
        add(crearCuerpo(),  BorderLayout.CENTER);
        add(crearFooter(),  BorderLayout.SOUTH);

        setSize(980, 680);
        setLocationRelativeTo(null);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel titulo = new JLabel("Personalizar el pedido");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Elige las opciones disponibles y personaliza la orden");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 15));
        sub.setForeground(new Color(255, 255, 255, 210));

        header.add(titulo);
        header.add(Box.createVerticalStrut(4));
        header.add(sub);
        return header;
    }

    private JScrollPane crearCuerpo() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(20, 32, 20, 32));

        List<Platillo> todosLosPlatillos = (restaurante.getMenu() != null)
            ? restaurante.getMenu().getListaPlatillos()
            : new ArrayList<>();

        body.add(crearSeccionHeader("Platillo Principal"));
        body.add(Box.createVerticalStrut(10));

        List<Platillo> extras = new ArrayList<>();
        for (Platillo p : todosLosPlatillos) {
            if (!p.getNombrePlatillo().equals(platilloPrincipal.getNombrePlatillo())) {
                extras.add(p);
            }
        }

        if (!extras.isEmpty()) {
            body.add(crearGridPlatillos(extras));
        } else {
            JLabel noExtras = new JLabel("  No hay platillos adicionales disponibles.");
            noExtras.setFont(new Font("SansSerif", Font.ITALIC, 13));
            noExtras.setForeground(new Color(120, 120, 120));
            body.add(noExtras);
        }

        body.add(Box.createVerticalStrut(16));

        List<Area> areas = restaurante.getAreas() != null
            ? restaurante.getAreas() : new ArrayList<>();

        body.add(crearSeccionHeader("Área / Complemento"));
        body.add(Box.createVerticalStrut(10));

        if (!areas.isEmpty()) {
            body.add(crearGridAreas(areas));
        } else {
            JLabel noAreas = new JLabel("  No hay áreas registradas para este restaurante.");
            noAreas.setFont(new Font("SansSerif", Font.ITALIC, 13));
            noAreas.setForeground(new Color(120, 120, 120));
            body.add(noAreas);
        }

        body.add(Box.createVerticalStrut(16));

        JScrollPane scroll = new JScrollPane(body);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearSeccionHeader(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        panel.setBackground(NARANJA);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(lbl, BorderLayout.WEST);
        return panel;
    }

    private JPanel crearGridPlatillos(List<Platillo> platillos) {
        JPanel grid = new JPanel(new GridLayout(0, 2, 10, 10));
        grid.setBackground(FONDO);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        for (Platillo p : platillos) {
            JPanel opcion = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
            opcion.setBackground(Color.WHITE);
            opcion.setBorder(BorderFactory.createLineBorder(new Color(229, 224, 216), 1));

            String label = p.getNombrePlatillo()
                + (p.getPrecio() != null ? "  $" + String.format("%.2f", p.getPrecio()) : "");
            JCheckBox cb = new JCheckBox(label);
            cb.setBackground(Color.WHITE);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
            cb.putClientProperty("platillo", p);
            checksPlatillos.add(cb);
            opcion.add(cb);
            grid.add(opcion);
        }
        return grid;
    }

    private JPanel crearGridAreas(List<Area> areas) {
        JPanel grid = new JPanel(new GridLayout(0, 2, 10, 10));
        grid.setBackground(FONDO);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        ButtonGroup grupo = new ButtonGroup();
        for (Area a : areas) {
            JPanel opcion = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
            opcion.setBackground(Color.WHITE);
            opcion.setBorder(BorderFactory.createLineBorder(new Color(229, 224, 216), 1));

            String label = a.getNombre()
                + (a.getCapacidadMax() != null ? "  (cap. " + a.getCapacidadMax() + ")" : "");
            JRadioButton rb = new JRadioButton(label);
            rb.setBackground(Color.WHITE);
            rb.setFont(new Font("SansSerif", Font.PLAIN, 13));
            rb.putClientProperty("area", a);
            grupo.add(rb);
            radiosArea.add(rb);
            opcion.add(rb);
            grid.add(opcion);
        }
        return grid;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 14));
        footer.setBackground(FONDO);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(229, 224, 216)));

        JButton btnConfirmar = crearBoton("Confirmar");
        btnConfirmar.addActionListener(e -> onConfirmar());

        JButton btnCancelar = crearBoton("Cancelar");
        btnCancelar.addActionListener(e ->
            Navegador.ir(new PantallaSeleccionarPlatillo(usuarioActual, restaurante, restaurantes))
        );

        footer.add(btnConfirmar);
        footer.add(btnCancelar);
        return footer;
    }

    private void onConfirmar() {
        List<Platillo> extrasSeleccionados = new ArrayList<>();
        for (JCheckBox cb : checksPlatillos) {
            if (cb.isSelected()) {
                extrasSeleccionados.add((Platillo) cb.getClientProperty("platillo"));
            }
        }

        Area areaSeleccionada = null;
        for (JRadioButton rb : radiosArea) {
            if (rb.isSelected()) {
                areaSeleccionada = (Area) rb.getClientProperty("area");
                break;
            }
        }

        if (areaSeleccionada == null && !radiosArea.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor selecciona un área para tu reservación.",
                "Área requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Navegador.ir(new PantallaPreferenciasReservacion(
            usuarioActual, restaurante, platilloPrincipal,
            extrasSeleccionados, areaSeleccionada, restaurantes
        ));
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(NARANJA);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(160, 42));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
