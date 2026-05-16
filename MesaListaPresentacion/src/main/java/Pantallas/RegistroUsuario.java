package Pantallas;

import DTO.UsuarioDTO;
import Control.ControlOperaciones;
import Utils.Navegador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class RegistroUsuario extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtAnioNacimiento;

    public RegistroUsuario() {
        setTitle("MesaLista - Crear Cuenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(24, 32, 20, 32));

        JLabel titulo = new JLabel("Crear Cuenta");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Únete y comienza a reservar");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(255, 255, 255, 210));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(4));
        header.add(sub);
        return header;
    }

    private JPanel crearCuerpo() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(28, 48, 28, 48));

        txtNombre          = crearCampo(body, "Nombre completo");
        body.add(Box.createVerticalStrut(16));
        txtEmail           = crearCampo(body, "Correo electrónico");
        body.add(Box.createVerticalStrut(16));
        txtAnioNacimiento  = crearCampo(body, "Año de nacimiento (YYYY)");
        body.add(Box.createVerticalStrut(24));

        JButton btnRegistrar = new JButton("Registrarme");
        btnRegistrar.setBackground(NARANJA);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setOpaque(true);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegistrar.addActionListener(this::onRegistrarClick);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(229, 224, 216));

        JButton btnVolver = new JButton("¿Ya tienes cuenta? Inicia sesión");
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setForeground(NARANJA);
        btnVolver.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnVolver.addActionListener(e -> new Navegador().ir(this, new InicioSesion()));

        body.add(btnRegistrar);
        body.add(Box.createVerticalStrut(16));
        body.add(sep);
        body.add(Box.createVerticalStrut(12));
        body.add(btnVolver);
        return body;
    }

    private JTextField crearCampo(JPanel parent, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 204, 196), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        field.setBackground(Color.WHITE);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(lbl);
        parent.add(Box.createVerticalStrut(5));
        parent.add(field);
        return field;
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
