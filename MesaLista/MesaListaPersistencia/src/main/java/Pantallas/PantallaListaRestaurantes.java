package Pantallas;

import Entidades.Restaurante;
import Entidades.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pantalla 1: Lista de restaurantes disponibles.
 * Muestra tarjetas de cada restaurante con su disponibilidad
 * y botón para hacer una reservación.
 */
public class PantallaListaRestaurantes extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private final Usuario usuarioActual;
    private final List<Restaurante> restaurantes;

    public PantallaListaRestaurantes(Usuario usuario, List<Restaurante> restaurantes) {
        this.usuarioActual = usuario;
        this.restaurantes  = restaurantes;

        setTitle("MesaLista - Restaurantes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearHeader(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);

        setSize(980, 560);
        setLocationRelativeTo(null);
    }

    // ── Header con nombre de usuario ──────────────────────────
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        izq.setOpaque(false);

        // Avatar circular
        JPanel avatar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(200, 200, 200));
                g2.fillOval(0, 0, 52, 52);
                g2.setColor(new Color(150, 150, 150));
                g2.fillOval(17, 8, 18, 18);
                g2.fillOval(9, 30, 34, 24);
            }
        };
        avatar.setPreferredSize(new Dimension(52, 52));
        avatar.setOpaque(false);

        JLabel lblNombre = new JLabel(usuarioActual.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);

        izq.add(avatar);
        izq.add(lblNombre);

        JButton btnMisRes = new JButton("Mis Reservaciones ›");
        btnMisRes.setForeground(Color.WHITE);
        btnMisRes.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 1, true));
        btnMisRes.setContentAreaFilled(false);
        btnMisRes.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnMisRes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnMisRes.addActionListener(e ->
            Navegador.ir(new PantallaMisReservaciones(usuarioActual, restaurantes))
        );

        header.add(izq,        BorderLayout.WEST);
        header.add(btnMisRes,  BorderLayout.EAST);
        return header;
    }

    // ── Grid de tarjetas de restaurantes ─────────────────────
    private JScrollPane crearContenido() {
        int cols = Math.min(restaurantes.size(), 3);
        JPanel grid = new JPanel(new GridLayout(
            (int) Math.ceil((double) restaurantes.size() / cols), cols, 28, 28));
        grid.setBackground(FONDO);
        grid.setBorder(BorderFactory.createEmptyBorder(36, 40, 40, 40));

        for (Restaurante r : restaurantes) {
            grid.add(crearTarjeta(r));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearTarjeta(Restaurante r) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(FONDO);

        // Imagen placeholder con inicial del restaurante
        JPanel imgPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(180, 160, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 48));
                String letra = r.getNombre() != null && !r.getNombre().isEmpty()
                    ? String.valueOf(r.getNombre().charAt(0)).toUpperCase() : "R";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(letra,
                    (getWidth()  - fm.stringWidth(letra)) / 2,
                    (getHeight() + fm.getAscent()) / 2 - 10);
            }
        };
        imgPanel.setPreferredSize(new Dimension(260, 200));
        imgPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        imgPanel.setOpaque(false);
        imgPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNombre = new JLabel(r.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Capacidad como indicador de disponibilidad
        String estadoTexto;
        Color estadoColor;
        int cap = r.getCapacidadTotal() != null ? r.getCapacidadTotal() : 0;
        if (cap > 50) {
            estadoTexto = "Disponible";
            estadoColor = new Color(34, 197, 94);
        } else if (cap > 10) {
            estadoTexto = "Poca disponibilidad";
            estadoColor = new Color(191, 223, 58);
        } else {
            estadoTexto = "No disponible";
            estadoColor = new Color(239, 68, 68);
        }

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        acciones.setOpaque(false);
        acciones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRes = new JButton("Reservaciones");
        btnRes.setBackground(NARANJA);
        btnRes.setForeground(Color.WHITE);
        btnRes.setOpaque(true);
        btnRes.setBorderPainted(false);
        btnRes.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnRes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRes.addActionListener(e ->
            Navegador.ir(new PantallaSeleccionarPlatillo(usuarioActual, r, restaurantes))
        );

        acciones.add(btnRes);
        acciones.add(crearBadge(estadoTexto, estadoColor));

        card.add(imgPanel);
        card.add(Box.createVerticalStrut(10));
        card.add(lblNombre);
        card.add(Box.createVerticalStrut(8));
        card.add(acciones);
        return card;
    }

    private JPanel crearBadge(String texto, Color color) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        badge.setOpaque(false);

        JPanel dot = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(color);
                g.fillOval(0, 2, 14, 14);
            }
        };
        dot.setPreferredSize(new Dimension(14, 18));
        dot.setOpaque(false);

        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(80, 80, 80));

        badge.add(dot);
        badge.add(lbl);
        return badge;
    }
}
