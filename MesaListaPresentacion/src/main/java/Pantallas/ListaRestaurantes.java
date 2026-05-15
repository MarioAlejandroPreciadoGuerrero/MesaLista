/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import Control.ControlOperaciones;
import DTO.RestauranteDTO;
import Utils.Navegador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.*;


/**
 *
 * @author USER
 */
public class ListaRestaurantes extends JFrame {

    private final String usuarioId;

    public ListaRestaurantes(String usuarioId) {
        this.usuarioId = usuarioId;
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
        
        JButton btnVerHistorial = new JButton("Ver Mis Reservaciones");
        btnVerHistorial.setBackground(new Color(102, 153, 255));
        btnVerHistorial.setForeground(Color.WHITE);
        btnVerHistorial.addActionListener(e -> new Utils.Navegador().ir(this, new Pantallas.HistorialReservaciones(usuarioId)));
        
        
        header.add(btnVerHistorial);

        List<RestauranteDTO> restaurantesBD = ControlOperaciones.getInstancia().obtenerTodosLosRestaurantes();

        if (restaurantesBD != null && !restaurantesBD.isEmpty()) {
            for (RestauranteDTO rest : restaurantesBD) {
                JButton btnRestaurante = new JButton(rest.getNombre());
                btnRestaurante.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                btnRestaurante.addActionListener(e -> new Navegador().ir(this, new SeleccionarPedido(usuarioId, rest.getId(), rest.getNombre())));
                body.add(btnRestaurante);
                body.add(Box.createVerticalStrut(15));
            }
        } else {
            body.add(new JLabel("No hay restaurantes disponibles en este momento."));
        }

        add(new JScrollPane(body), BorderLayout.CENTER);
    }
}
