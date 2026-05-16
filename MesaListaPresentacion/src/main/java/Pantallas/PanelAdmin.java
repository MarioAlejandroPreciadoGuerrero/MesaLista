package Pantallas;

import Utils.Navegador;

import javax.swing.*;
import java.awt.*;

public class PanelAdmin extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);
    private static final Color AZUL    = new Color(59, 130, 246);

    private final String restauranteId;
    private final String nombreRestaurante;

    public PanelAdmin(String restauranteId, String nombreRestaurante) {
        this.restauranteId     = restauranteId;
        this.nombreRestaurante = nombreRestaurante;

        setTitle("MesaLista - Panel de " + nombreRestaurante);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Panel de Administración");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel(nombreRestaurante);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(255, 255, 255, 210));

        izq.add(titulo);
        izq.add(Box.createVerticalStrut(4));
        izq.add(sub);

        JButton btnVolver = new JButton("‹ Salir");
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

    private JPanel crearCuerpo() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

        JLabel lblPregunta = new JLabel("¿Qué deseas administrar?");
        lblPregunta.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblPregunta.setForeground(new Color(80, 80, 80));
        lblPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);

        body.add(lblPregunta);
        body.add(Box.createVerticalStrut(40));

        JButton btnAreas = crearBotonOpcion(
            "Administrar Áreas",
            "Gestiona las áreas y mesas del restaurante",
            AZUL
        );
        btnAreas.addActionListener(e ->
            new Navegador().ir(this, new AdministrarAreas(restauranteId, nombreRestaurante))
        );

        JButton btnMenu = crearBotonOpcion(
            "Administrar Menú",
            "Agrega, edita o elimina platillos del menú",
            NARANJA
        );
        btnMenu.addActionListener(e ->
            new Navegador().ir(this, new AdministrarMenu(restauranteId, nombreRestaurante))
        );

        body.add(btnAreas);
        body.add(Box.createVerticalStrut(20));
        body.add(btnMenu);

        return body;
    }

    private JButton crearBotonOpcion(String titulo, String descripcion, Color color) {
        JButton btn = new JButton("<html><b>" + titulo + "</b><br><small>" + descripcion + "</small></html>");
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 70));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        return btn;
    }
}
