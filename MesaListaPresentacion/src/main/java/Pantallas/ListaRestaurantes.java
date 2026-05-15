/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;




import Facade.RestaurantesFacade;
import Interface.IRestaurantesFacade;
import Utils.Navegador;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author USER
 */
public class ListaRestaurantes extends JFrame {

    private final IRestaurantesFacade restaurantesFacade;
    private final String usuarioId;

    public ListaRestaurantes(String usuarioId) {
        this.usuarioId = usuarioId;
        this.restaurantesFacade = new RestaurantesFacade();
        configurarVentana();
        agregarComponentes();
    }

    private void configurarVentana() {
        setTitle("MesaLista - Restaurantes Disponibles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void agregarComponentes() {
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 153, 51));
        JLabel lblTitulo = new JLabel("Elige un Restaurante");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(lblTitulo);
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // En un caso real, pedirías la lista a la Fachada. Aquí simulamos la respuesta visual.
        String[][] restaurantesMoficados = {
            {"ID_1", "Rincón el Asador"},
            {"ID_2", "El Deshuesadero"},
            {"ID_3", "Mariscos el Rey"}
        };

        for (String[] rest : restaurantesMoficados) {
            JButton btnRestaurante = new JButton(rest[1]);
            btnRestaurante.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            btnRestaurante.addActionListener(e -> new Navegador().ir(this, new SeleccionarPedido(usuarioId, rest[0], rest[1])));
            body.add(btnRestaurante);
            body.add(Box.createVerticalStrut(15));
        }

        add(new JScrollPane(body), BorderLayout.CENTER);
    }
}
