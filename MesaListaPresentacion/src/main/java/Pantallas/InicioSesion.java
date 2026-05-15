/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pantallas;

import DTO.UsuarioDTO;
import Control.ControlOperaciones;
import Utils.Navegador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author USER
 */
public class InicioSesion extends JFrame {

    private JTextField txtEmail;

    public InicioSesion() {
        configurarVentana();
        agregarComponentes();
    }

    private void configurarVentana() {
        setTitle("MesaLista - Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 153, 51));
        JLabel lblTitulo = new JLabel("Bienvenido a MesaLista");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(lblTitulo);
        add(header, BorderLayout.NORTH);

        
        JPanel body = new JPanel(new GridLayout(3, 1, 10, 10));
        body.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        body.setBackground(new Color(245, 245, 245));

        body.add(new JLabel("Correo Electrónico:"));
        txtEmail = new JTextField();
        body.add(txtEmail);

        JButton btnEntrar = new JButton("Ingresar");
        btnEntrar.setBackground(new Color(255, 153, 51));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.addActionListener(this::onEntrarClick);
        body.add(btnEntrar);

        add(body, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        JButton btnRegistrar = new JButton("¿No tienes cuenta? Regístrate");
        btnRegistrar.addActionListener(e -> new Navegador().ir(this, new RegistroUsuario()));
        footer.add(btnRegistrar);
        add(footer, BorderLayout.SOUTH);
    }

    private void onEntrarClick(ActionEvent e) {
        try {
            String email = txtEmail.getText().trim();

            
            UsuarioDTO usuarioLogueado = ControlOperaciones.getInstancia().iniciarSesion(email);

            JOptionPane.showMessageDialog(this, "Bienvenido " + usuarioLogueado.getNombre());
            new Navegador().ir(this, new ListaRestaurantes(usuarioLogueado.getId()));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Acceso", JOptionPane.ERROR_MESSAGE);
        }
    }
}
