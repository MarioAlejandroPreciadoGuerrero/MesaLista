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
import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class RegistroUsuario extends JFrame {

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtAnioNacimiento; 

    public RegistroUsuario() {
        configurarVentana();
        agregarComponentes();
    }

    private void configurarVentana() {
        setTitle("MesaLista - Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void agregarComponentes() {
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 153, 51));
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(lblTitulo);
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new GridLayout(7, 1, 5, 5));
        body.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        body.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        body.add(txtNombre);

        body.add(new JLabel("Correo Electrónico:"));
        txtEmail = new JTextField();
        body.add(txtEmail);

        body.add(new JLabel("Año de Nacimiento (YYYY):"));
        txtAnioNacimiento = new JTextField();
        body.add(txtAnioNacimiento);

        JButton btnRegistrar = new JButton("Registrarme");
        btnRegistrar.setBackground(new Color(255, 153, 51));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.addActionListener(this::onRegistrarClick);
        body.add(btnRegistrar);

        add(body, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        JButton btnVolver = new JButton("Volver al Login");
        btnVolver.addActionListener(e -> new Navegador().ir(this, new InicioSesion()));
        footer.add(btnVolver);
        add(footer, BorderLayout.SOUTH);
    }

    private void onRegistrarClick(ActionEvent e) {
        try {
            UsuarioDTO nuevoUsuario = new UsuarioDTO();
            nuevoUsuario.setNombre(txtNombre.getText().trim());
            nuevoUsuario.setEmail(txtEmail.getText().trim());

            int anio = Integer.parseInt(txtAnioNacimiento.getText().trim());
            nuevoUsuario.setFechaNacimiento(LocalDate.of(anio, 1, 1));

            ControlOperaciones.getInstancia().registrarNuevoUsuario(nuevoUsuario);

            JOptionPane.showMessageDialog(this, "¡Cuenta creada exitosamente!");
            new Navegador().ir(this, new InicioSesion());

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "El año de nacimiento debe ser un número válido (Ej. 1995).", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }
}
