package Pantallas;

import Entidades.Reservacion;
import Entidades.Restaurante;
import Entidades.Usuario;
import Repository.ReservacionRepository;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Pantalla 5: Mis Reservaciones.
 * Muestra todas las reservaciones del usuario actual en tarjetas
 * con la misma estética naranja del resto de la app.
 * Permite cancelar (eliminar) una reservación.
 */
public class PantallaMisReservaciones extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);
    private static final Color ROJO    = new Color(220, 53, 69);

    private final Usuario            usuarioActual;
    private final List<Restaurante>  restaurantes; // para poder volver
    private JPanel panelLista;

    /**
     * Constructor que recibe la lista de restaurantes para poder regresar.
     */
    public PantallaMisReservaciones(Usuario usuario, List<Restaurante> restaurantes) {
        this.usuarioActual = usuario;
        this.restaurantes  = restaurantes;

        setTitle("Mis Reservaciones - " + usuario.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(crearHeader(),    BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);

        setSize(820, 620);
        setLocationRelativeTo(null);
    }

    // ── Header ────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Mis Reservaciones");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Historial de reservaciones de " + usuarioActual.getNombre());
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
            Navegador.ir(new PantallaListaRestaurantes(usuarioActual, restaurantes))
        );

        header.add(izq,       BorderLayout.WEST);
        header.add(btnVolver, BorderLayout.EAST);
        return header;
    }

    // ── Contenido principal ───────────────────────────────────
    private JScrollPane crearContenido() {
        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(FONDO);
        panelLista.setBorder(BorderFactory.createEmptyBorder(24, 36, 24, 36));

        cargarReservaciones();

        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // ── Carga reservaciones del usuario desde MongoDB ─────────
    private void cargarReservaciones() {
        panelLista.removeAll();

        List<Reservacion> reservaciones = obtenerReservacionesDelUsuario();

        if (reservaciones.isEmpty()) {
            JPanel vacio = new JPanel(new BorderLayout());
            vacio.setBackground(FONDO);
            vacio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

            JLabel ico = new JLabel("📋", SwingConstants.CENTER);
            ico.setFont(new Font("SansSerif", Font.PLAIN, 48));

            JLabel msg = new JLabel("No tienes reservaciones registradas.", SwingConstants.CENTER);
            msg.setFont(new Font("SansSerif", Font.PLAIN, 16));
            msg.setForeground(new Color(120, 120, 120));

            JPanel centro = new JPanel();
            centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
            centro.setOpaque(false);
            ico.setAlignmentX(Component.CENTER_ALIGNMENT);
            msg.setAlignmentX(Component.CENTER_ALIGNMENT);
            centro.add(Box.createVerticalStrut(40));
            centro.add(ico);
            centro.add(Box.createVerticalStrut(12));
            centro.add(msg);

            vacio.add(centro, BorderLayout.CENTER);
            panelLista.add(vacio);
        } else {
            for (Reservacion r : reservaciones) {
                panelLista.add(crearTarjetaReservacion(r));
                panelLista.add(Box.createVerticalStrut(14));
            }
        }

        panelLista.revalidate();
        panelLista.repaint();
    }

    /**
     * Obtiene las reservaciones del usuario actual desde MongoDB.
     * Si hay error de conexión, devuelve lista vacía con aviso.
     */
    private List<Reservacion> obtenerReservacionesDelUsuario() {
        try {
            ReservacionRepository repo = new ReservacionRepository();
            return repo.buscarPorUsuarioId(usuarioActual.getId());
        } catch (Exception e) {
            SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this,
                    "No se pudo conectar a la base de datos.\n" + e.getMessage(),
                    "Aviso", JOptionPane.WARNING_MESSAGE)
            );
            return new ArrayList<>();
        }
    }

    // ── Tarjeta de reservación ────────────────────────────────
    private JPanel crearTarjetaReservacion(Reservacion r) {
        JPanel card = new JPanel(new BorderLayout(16, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 224, 216), 1),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        // ── Indicador lateral naranja ──────────────────────────
        JPanel barra = new JPanel();
        barra.setBackground(NARANJA);
        barra.setPreferredSize(new Dimension(5, 0));

        // ── Información principal ──────────────────────────────
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel lblFolio = new JLabel("Folio: " + (r.getFolio() != null ? r.getFolio() : "N/A"));
        lblFolio.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblFolio.setForeground(NARANJA);

        String fechaStr = "Fecha: ";
        if (r.getFechaHora() != null) {
            fechaStr += r.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  hh:mma"));
        } else {
            fechaStr += "No especificada";
        }
        JLabel lblFecha = new JLabel(fechaStr);
        lblFecha.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblFecha.setForeground(new Color(60, 60, 60));

        JLabel lblPersonas = new JLabel("Personas: " + (r.getNumPersonas() != null ? r.getNumPersonas() : "—"));
        lblPersonas.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblPersonas.setForeground(new Color(60, 60, 60));

        JLabel lblArea = new JLabel("Área: " + (r.getAreaNombre() != null && !r.getAreaNombre().isEmpty()
            ? r.getAreaNombre() : "Sin especificar"));
        lblArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblArea.setForeground(new Color(60, 60, 60));

        String costoStr = r.getCosto() != null ? String.format("$%.2f", r.getCosto()) : "—";
        JLabel lblCosto = new JLabel("Costo total: " + costoStr);
        lblCosto.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCosto.setForeground(new Color(40, 40, 40));

        info.add(lblFolio);
        info.add(Box.createVerticalStrut(4));
        info.add(lblFecha);
        info.add(Box.createVerticalStrut(2));
        info.add(lblPersonas);
        info.add(Box.createVerticalStrut(2));
        info.add(lblArea);
        info.add(Box.createVerticalStrut(4));
        info.add(lblCosto);

        // ── Botón cancelar ────────────────────────────────────
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(ROJO);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setOpaque(true);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnCancelar.setPreferredSize(new Dimension(110, 36));
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> cancelarReservacion(r));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        acciones.setOpaque(false);
        acciones.add(btnCancelar);

        card.add(barra,    BorderLayout.WEST);
        card.add(info,     BorderLayout.CENTER);
        card.add(acciones, BorderLayout.EAST);
        return card;
    }

    // ── Cancelar reservación ──────────────────────────────────
    private void cancelarReservacion(Reservacion r) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas cancelar la reservación " + r.getFolio() + "?",
            "Cancelar reservación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ReservacionRepository repo = new ReservacionRepository();
                repo.eliminar(r.getId());
                JOptionPane.showMessageDialog(this,
                    "Reservación " + r.getFolio() + " cancelada correctamente.",
                    "Cancelada", JOptionPane.INFORMATION_MESSAGE);
                cargarReservaciones(); // refrescar lista
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al cancelar:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
