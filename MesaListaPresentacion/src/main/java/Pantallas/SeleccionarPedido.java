package Pantallas;

import Control.ControlOperaciones;
import DTO.PlatilloDTO;
import DTO.RestauranteDTO;
import Utils.Navegador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SeleccionarPedido extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private final String usuarioId;
    private final String nombreUsuario;
    private final String restauranteId;
    private final String nombreRestaurante;

    private JPanel[] tarjetas;

    public SeleccionarPedido(String usuarioId, String nombreUsuario,
                              String restauranteId, String nombreRestaurante) {
        this.usuarioId         = usuarioId;
        this.nombreUsuario     = nombreUsuario;
        this.restauranteId     = restauranteId;
        this.nombreRestaurante = nombreRestaurante;

        setTitle("MesaLista - Menú de " + nombreRestaurante);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(980, 580);
        setLocationRelativeTo(null);

        add(crearHeader(),    BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
    }

    // ── Header ─────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Selecciona el pedido");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Elige las opciones disponibles y personaliza la orden");
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
        btnVolver.addActionListener(e ->
            new Navegador().ir(this, new ListaRestaurantes(usuarioId, nombreUsuario))
        );

        header.add(izq,       BorderLayout.WEST);
        header.add(btnVolver, BorderLayout.EAST);
        return header;
    }

    // ── Contenido (grid de tarjetas de platillos) ───────────────────────────
    private JScrollPane crearContenido() {
        RestauranteDTO restaurante = ControlOperaciones.getInstancia()
            .obtenerDetallesRestaurante(restauranteId);

        List<PlatilloDTO> platillos = (restaurante != null && restaurante.getMenu() != null)
            ? restaurante.getMenu().getListaPlatillos() : null;

        if (platillos == null || platillos.isEmpty()) {
            JPanel vacio = new JPanel(new BorderLayout());
            vacio.setBackground(FONDO);
            JLabel lbl = new JLabel("No hay platillos disponibles en este restaurante.", SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lbl.setForeground(new Color(120, 120, 120));
            vacio.add(lbl, BorderLayout.CENTER);
            return new JScrollPane(vacio);
        }

        int cols = Math.min(platillos.size(), 3);
        int rows = (int) Math.ceil((double) platillos.size() / cols);

        JPanel grid = new JPanel(new GridLayout(rows, cols, 24, 24));
        grid.setBackground(FONDO);
        grid.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));

        tarjetas = new JPanel[platillos.size()];
        for (int i = 0; i < platillos.size(); i++) {
            final int idx = i;
            final PlatilloDTO p = platillos.get(i);
            tarjetas[i] = crearTarjetaPlatillo(p, () -> seleccionar(idx, p));
            grid.add(tarjetas[i]);
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearTarjetaPlatillo(PlatilloDTO p, Runnable alSeleccionar) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(FONDO);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 215, 208), 1));

        // Zona de imagen / ícono
        JPanel imgPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 228, 210));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(180, 150, 110));
                g2.setFont(new Font("SansSerif", Font.PLAIN, 48));
                String ic = "🍽";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(ic, (getWidth() - fm.stringWidth(ic)) / 2, getHeight() / 2 + 16);
            }
        };
        imgPanel.setPreferredSize(new Dimension(0, 150));

        // Zona inferior: nombre, precio, descripción, botón
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel lblNombre = new JLabel(p.getNombrePlatillo(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNombre.setBorder(BorderFactory.createEmptyBorder(8, 0, 2, 0));

        String precioStr = p.getPrecio() != null ? String.format("$%.2f", p.getPrecio()) : "";
        JLabel lblPrecio = new JLabel(precioStr, SwingConstants.CENTER);
        lblPrecio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblPrecio.setForeground(new Color(100, 100, 100));
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
            JLabel lblDesc = new JLabel(
                "<html><center>" + p.getDescripcion() + "</center></html>", SwingConstants.CENTER);
            lblDesc.setFont(new Font("SansSerif", Font.ITALIC, 12));
            lblDesc.setForeground(new Color(140, 130, 120));
            lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblDesc.setBorder(BorderFactory.createEmptyBorder(2, 4, 4, 4));
            info.add(lblDesc);
        }

        info.add(lblNombre);
        info.add(lblPrecio);

        JButton btn = new JButton("Seleccionar");
        btn.setBackground(NARANJA);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> alSeleccionar.run());

        bottom.add(info, BorderLayout.NORTH);
        bottom.add(btn,  BorderLayout.SOUTH);

        card.add(imgPanel, BorderLayout.CENTER);
        card.add(bottom,   BorderLayout.SOUTH);
        return card;
    }

    private void seleccionar(int idx, PlatilloDTO p) {
        // Resaltar tarjeta seleccionada
        for (int i = 0; i < tarjetas.length; i++) {
            boolean sel = (i == idx);
            tarjetas[i].setBorder(BorderFactory.createLineBorder(
                sel ? NARANJA : new Color(220, 215, 208), sel ? 2 : 1));
        }

        Timer t = new Timer(180, e ->
            new Navegador().ir(this, new PreferenciasReservacion(
                usuarioId, nombreUsuario, restauranteId, p.getNombrePlatillo(), p.getPrecio()))
        );
        t.setRepeats(false);
        t.start();
    }
}
