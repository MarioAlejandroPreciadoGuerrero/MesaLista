package Pantallas;

import Control.ControlOperaciones;
import DTO.RestauranteDTO;
import Utils.Navegador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SeleccionarRestauranteAdmin extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    public SeleccionarRestauranteAdmin() {
        setTitle("MesaLista - Administrar Restaurante");
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
        header.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Panel de administración");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Selecciona un restaurante para gestionar sus áreas o menú");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setForeground(new Color(255, 255, 255, 210));

        izq.add(titulo);
        izq.add(Box.createVerticalStrut(3));
        izq.add(sub);

        JButton btnVolver = new JButton("‹ Volver");
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 1, true));
        btnVolver.setContentAreaFilled(false);
        btnVolver.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> new Navegador().ir(this, new InicioSesion()));

        header.add(izq,       BorderLayout.WEST);
        header.add(btnVolver, BorderLayout.EAST);
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
            JLabel lbl = new JLabel("No hay restaurantes registrados.", SwingConstants.CENTER);
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
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 215, 208), 1));

        // Cabecera de la tarjeta
        JPanel cabecera = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(180, 160, 140));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 42));
                String letra = (r.getNombre() != null && !r.getNombre().isEmpty())
                        ? String.valueOf(r.getNombre().charAt(0)).toUpperCase() : "R";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(letra,
                        (getWidth() - fm.stringWidth(letra)) / 2,
                        (getHeight() + fm.getAscent()) / 2 - 8);
            }
        };
        cabecera.setPreferredSize(new Dimension(0, 140));

        // Info y botones
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);
        info.setBorder(BorderFactory.createEmptyBorder(12, 16, 16, 16));

        JLabel lblNombre = new JLabel(r.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        int cap = r.getCapacidadTotal() != null ? r.getCapacidadTotal() : 0;
        JLabel lblCap = new JLabel("Capacidad total: " + cap + " personas");
        lblCap.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblCap.setForeground(new Color(120, 120, 120));
        lblCap.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel botones = new JPanel(new GridLayout(1, 2, 8, 0));
        botones.setOpaque(false);
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);
        botones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnAreas = crearBotonAccion("Áreas", new Color(59, 130, 246));
        btnAreas.addActionListener(e ->
            new Navegador().ir(this, new AdministrarAreas(r.getId(), r.getNombre()))
        );

        JButton btnMenu = crearBotonAccion("Menú", NARANJA);
        btnMenu.addActionListener(e ->
            new Navegador().ir(this, new AdministrarMenu(r.getId(), r.getNombre()))
        );

        botones.add(btnAreas);
        botones.add(btnMenu);

        info.add(lblNombre);
        info.add(Box.createVerticalStrut(4));
        info.add(lblCap);
        info.add(botones);

        card.add(cabecera, BorderLayout.CENTER);
        card.add(info,     BorderLayout.SOUTH);
        return card;
    }

    private JButton crearBotonAccion(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 36));
        return btn;
    }
}
