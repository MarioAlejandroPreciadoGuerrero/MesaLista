package Pantallas;

import Control.ControlOperaciones;
import DTO.RestauranteDTO;
import Utils.Navegador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListaRestaurantes extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private final String usuarioId;
    private final String nombreUsuario;

    public ListaRestaurantes(String usuarioId, String nombreUsuario) {
        this.usuarioId     = usuarioId;
        this.nombreUsuario = nombreUsuario;

        setTitle("MesaLista - Restaurantes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(980, 560);
        setLocationRelativeTo(null);

        add(crearHeader(),    BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnVerHistorial = new JButton("Ver Mis Reservaciones");
        btnVerHistorial.setBackground(new Color(102, 153, 255));
        btnVerHistorial.setForeground(Color.WHITE);
        btnVerHistorial.addActionListener(e -> new Utils.Navegador().ir(this, new Pantallas.HistorialReservaciones(usuarioId)));
        
        
        header.add(btnVerHistorial);

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

        JLabel lblNombre = new JLabel(nombreUsuario != null ? nombreUsuario : "Usuario");
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
            JOptionPane.showMessageDialog(this, "Funcionalidad de reservaciones próximamente.")
        );

        header.add(izq,       BorderLayout.WEST);
        header.add(btnMisRes, BorderLayout.EAST);
        return header;
    }

    private JScrollPane crearContenido() {
        List<RestauranteDTO> restaurantes = ControlOperaciones.getInstancia().obtenerTodosLosRestaurantes();

        int cols = (restaurantes == null || restaurantes.isEmpty()) ? 1 : Math.min(restaurantes.size(), 3);
        int rows = (restaurantes == null || restaurantes.isEmpty()) ? 1
            : (int) Math.ceil((double) restaurantes.size() / cols);

        JPanel grid = new JPanel(new GridLayout(rows, cols, 28, 28));
        grid.setBackground(FONDO);
        grid.setBorder(BorderFactory.createEmptyBorder(36, 40, 40, 40));

        if (restaurantes == null || restaurantes.isEmpty()) {
            JLabel lbl = new JLabel("No hay restaurantes disponibles.", SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lbl.setForeground(new Color(120, 120, 120));
            grid.add(lbl);
        } else {
            for (RestauranteDTO r : restaurantes) {
                grid.add(crearTarjeta(r));
            }
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearTarjeta(RestauranteDTO r) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(FONDO);

        // Imagen placeholder con inicial
        JPanel imgPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(180, 160, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 48));
                String letra = (r.getNombre() != null && !r.getNombre().isEmpty())
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

        // Badge de disponibilidad según capacidad
        int cap = r.getCapacidadTotal() != null ? r.getCapacidadTotal() : 0;
        String estadoTexto;
        Color estadoColor;
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
            new Navegador().ir(this, new SeleccionarPedido(usuarioId, nombreUsuario, r.getId(), r.getNombre()))
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
