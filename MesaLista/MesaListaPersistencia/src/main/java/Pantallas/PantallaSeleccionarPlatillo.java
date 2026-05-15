package Pantallas;

import Entidades.Platillo;
import Entidades.Restaurante;
import Entidades.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pantalla 2: Selección de platillo del menú del restaurante.
 */
public class PantallaSeleccionarPlatillo extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private final Usuario           usuarioActual;
    private final Restaurante       restaurante;
    private final List<Restaurante> restaurantes; // lista completa para poder volver

    private JPanel[]  tarjetas;
    private Platillo  platilloSeleccionado;

    public PantallaSeleccionarPlatillo(Usuario usuario, Restaurante restaurante,
                                       List<Restaurante> restaurantes) {
        this.usuarioActual = usuario;
        this.restaurante   = restaurante;
        this.restaurantes  = restaurantes;

        setTitle("Selecciona tu platillo - " + restaurante.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearHeader(),    BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);

        setSize(980, 580);
        setLocationRelativeTo(null);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel titulo = new JLabel("Selecciona el pedido");
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

    private JScrollPane crearContenido() {
        List<Platillo> platillos = (restaurante.getMenu() != null)
            ? restaurante.getMenu().getListaPlatillos()
            : null;

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
        JPanel grid = new JPanel(new GridLayout(
            (int) Math.ceil((double) platillos.size() / cols), cols, 24, 24));
        grid.setBackground(FONDO);
        grid.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));

        tarjetas = new JPanel[platillos.size()];
        for (int i = 0; i < platillos.size(); i++) {
            final int idx = i;
            final Platillo p = platillos.get(i);
            tarjetas[i] = crearTarjetaPlatillo(p, () -> seleccionar(idx, p));
            grid.add(tarjetas[i]);
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearTarjetaPlatillo(Platillo p, Runnable alSeleccionar) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 215, 208), 1));

        JPanel img = new JPanel() {
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
        img.setPreferredSize(new Dimension(0, 150));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        JLabel lblNombre = new JLabel(p.getNombrePlatillo(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setBorder(BorderFactory.createEmptyBorder(6, 0, 2, 0));

        String precioStr = p.getPrecio() != null ? String.format("$%.2f", p.getPrecio()) : "";
        JLabel lblPrecio = new JLabel(precioStr, SwingConstants.CENTER);
        lblPrecio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblPrecio.setForeground(new Color(100, 100, 100));
        lblPrecio.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        JButton btn = new JButton("Seleccionar");
        btn.setBackground(NARANJA);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> alSeleccionar.run());

        JPanel labels = new JPanel();
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        labels.setOpaque(false);
        labels.add(lblNombre);
        labels.add(lblPrecio);

        bottom.add(labels, BorderLayout.NORTH);
        bottom.add(btn,    BorderLayout.SOUTH);

        card.add(img,    BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private void seleccionar(int idx, Platillo p) {
        platilloSeleccionado = p;

        for (int i = 0; i < tarjetas.length; i++) {
            boolean sel = (i == idx);
            tarjetas[i].setBorder(BorderFactory.createLineBorder(
                sel ? NARANJA : new Color(220, 215, 208), sel ? 2 : 1));
        }

        Timer t = new Timer(200, e ->
            Navegador.ir(new PantallaPersonalizarPedido(
                usuarioActual, restaurante, platilloSeleccionado, restaurantes))
        );
        t.setRepeats(false);
        t.start();
    }
}
