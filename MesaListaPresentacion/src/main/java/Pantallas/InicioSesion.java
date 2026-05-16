package Pantallas;

import DTO.UsuarioDTO;
import Control.ControlOperaciones;
import Utils.Navegador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InicioSesion extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private JTextField txtEmail;

    public InicioSesion() {
        setTitle("MesaLista - Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 440);
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
        header.setBorder(BorderFactory.createEmptyBorder(28, 32, 24, 32));

        JLabel logo = new JLabel("🍽  MesaLista");
        logo.setFont(new Font("SansSerif", Font.BOLD, 26));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Reserva tu mesa favorita en segundos");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sub.setForeground(new Color(255, 255, 255, 210));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(logo);
        header.add(Box.createVerticalStrut(6));
        header.add(sub);
        return header;
    }

    private JPanel crearCuerpo() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(32, 48, 32, 48));

        JLabel lblEmail = new JLabel("Correo electrónico");
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblEmail.setForeground(new Color(80, 80, 80));
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 204, 196), 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnEntrar = new JButton("Ingresar");
        btnEntrar.setBackground(NARANJA);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setOpaque(true);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEntrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEntrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEntrar.addActionListener(this::onEntrarClick);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(229, 224, 216));

        JButton btnRegistrar = new JButton("¿No tienes cuenta? Regístrate aquí");
        btnRegistrar.setContentAreaFilled(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setForeground(NARANJA);
        btnRegistrar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegistrar.addActionListener(e -> new Navegador().ir(this, new RegistroUsuario()));

        body.add(lblEmail);
        body.add(Box.createVerticalStrut(6));
        body.add(txtEmail);
        body.add(Box.createVerticalStrut(20));
        body.add(btnEntrar);
        body.add(Box.createVerticalStrut(18));
        body.add(sep);
        body.add(Box.createVerticalStrut(14));
        body.add(btnRegistrar);

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setForeground(new Color(229, 224, 216));
        body.add(Box.createVerticalStrut(14));
        body.add(sep2);
        body.add(Box.createVerticalStrut(14));

        JButton btnAdmin = new JButton("¿Dueño de restaurante? Administra aquí");
        btnAdmin.setContentAreaFilled(false);
        btnAdmin.setBorderPainted(false);
        btnAdmin.setForeground(new Color(100, 100, 100));
        btnAdmin.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdmin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAdmin.addActionListener(e -> onAdminClick());
        body.add(btnAdmin);

        return body;
    }

    private void onAdminClick() {
        JTextField txtUsuario = new JTextField();
        JPasswordField txtPass = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPass);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Acceso de Restaurante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) return;

        try {
            String usuario    = txtUsuario.getText().trim();
            String contrasena = new String(txtPass.getPassword()).trim();
            ControlOperaciones.getInstancia().iniciarSesionAdmin(usuario, contrasena);
            new Navegador().ir(this, new SeleccionarRestauranteAdmin());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEntrarClick(ActionEvent e) {
        try {
            String email = txtEmail.getText().trim();
            UsuarioDTO usuario = ControlOperaciones.getInstancia().iniciarSesion(email);
            new Navegador().ir(this, new ListaRestaurantes(usuario.getId(), usuario.getNombre()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Acceso", JOptionPane.ERROR_MESSAGE);
        }
    }
}
