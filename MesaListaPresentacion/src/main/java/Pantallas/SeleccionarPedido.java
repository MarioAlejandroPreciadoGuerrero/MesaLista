/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Utils.Navegador;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author USER
 */
public class SeleccionarPedido extends JFrame {

    private final String usuarioId;
    private final String restauranteId;
    private final String nombreRestaurante;

    public SeleccionarPedido(String usuarioId, String restauranteId, String nombreRestaurante) {
        this.usuarioId = usuarioId;
        this.restauranteId = restauranteId;
        this.nombreRestaurante = nombreRestaurante;
        configurarVentana();
        agregarComponentes();
    }

    private void configurarVentana() {
        setTitle("MesaLista - Menú");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void agregarComponentes() {
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 153, 51));
        JLabel lblTitulo = new JLabel("Menú de " + nombreRestaurante);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.add(lblTitulo);
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En un caso real, obtendrías los platillos del RestauranteDTO mediante la Fachada
        String[] platillos = {"Doble Smash Burger - $120", "Clásica BBQ - $110", "Pollo Crispy - $95"};

        for (String platillo : platillos) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            card.add(new JLabel("  " + platillo), BorderLayout.CENTER);

            JButton btnElegir = new JButton("Agregar");
            btnElegir.addActionListener(e -> {
                // Navegamos a las preferencias de reservación enviando el platillo seleccionado
                new Navegador().ir(this, new PreferenciasReservacion(usuarioId, restauranteId, platillo));
            });
            card.add(btnElegir, BorderLayout.EAST);

            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            body.add(card);
            body.add(Box.createVerticalStrut(10));
        }

        add(new JScrollPane(body), BorderLayout.CENTER);

        JPanel footer = new JPanel();
        JButton btnAtras = new JButton("Volver");
        btnAtras.addActionListener(e -> new Navegador().ir(this, new ListaRestaurantes(usuarioId)));
        footer.add(btnAtras);
        add(footer, BorderLayout.SOUTH);
    }
}
