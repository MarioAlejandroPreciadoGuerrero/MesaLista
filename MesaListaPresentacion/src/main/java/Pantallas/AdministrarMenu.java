package Pantallas;

import Control.ControlOperaciones;
import DTO.MenuDTO;
import DTO.PlatilloDTO;
import DTO.RestauranteDTO;
import Utils.Navegador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdministrarMenu extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);

    private final String restauranteId;
    private final String nombreRestaurante;

    private DefaultTableModel modeloTabla;
    private List<PlatilloDTO> platillosActuales;

    public AdministrarMenu(String restauranteId, String nombreRestaurante) {
        this.restauranteId     = restauranteId;
        this.nombreRestaurante = nombreRestaurante;

        setTitle("MesaLista - Menú de " + nombreRestaurante);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(750, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        cargarMenu();

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }

    private void cargarMenu() {
        RestauranteDTO r = ControlOperaciones.getInstancia().obtenerDetallesRestaurante(restauranteId);
        platillosActuales = (r != null && r.getMenu() != null && r.getMenu().getListaPlatillos() != null)
                ? new ArrayList<>(r.getMenu().getListaPlatillos()) : new ArrayList<>();
    }

    // ── Header ──────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Administrar Menú");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel(nombreRestaurante);
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
        btnVolver.addActionListener(e -> new Navegador().ir(this, new SeleccionarRestauranteAdmin()));

        header.add(izq,       BorderLayout.WEST);
        header.add(btnVolver, BorderLayout.EAST);
        return header;
    }

    // ── Cuerpo (tabla) ───────────────────────────────────────────────────────
    private JPanel crearCuerpo() {
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(FONDO);
        body.setBorder(BorderFactory.createEmptyBorder(20, 24, 12, 24));

        String[] columnas = {"Nombre del Platillo", "Precio ($)", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        refrescarTabla();

        JTable tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setRowHeight(26);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setGridColor(new Color(220, 215, 208));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(350);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 215, 208)));
        body.add(scroll, BorderLayout.CENTER);

        // Botones de acción sobre la tabla
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        acciones.setOpaque(false);

        JButton btnAgregar = crearBoton("＋ Agregar Platillo", NARANJA);
        btnAgregar.addActionListener(e -> onAgregarPlatillo(tabla));

        JButton btnEliminar = crearBoton("✕ Eliminar seleccionado", new Color(239, 68, 68));
        btnEliminar.addActionListener(e -> onEliminarPlatillo(tabla));

        acciones.add(btnAgregar);
        acciones.add(btnEliminar);
        body.add(acciones, BorderLayout.SOUTH);

        return body;
    }

    // ── Footer (guardar) ─────────────────────────────────────────────────────
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 24, 12));
        footer.setBackground(FONDO);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(229, 224, 216)));

        JButton btnGuardar = crearBoton("✓ Guardar cambios", NARANJA);
        btnGuardar.setPreferredSize(new Dimension(200, 40));
        btnGuardar.addActionListener(e -> onGuardar());

        footer.add(btnGuardar);
        return footer;
    }

    // ── Lógica: agregar platillo ──────────────────────────────────────────────
    private void onAgregarPlatillo(JTable tabla) {
        JTextField txtNombre = new JTextField(16);
        JTextField txtPrecio = new JTextField(8);
        JTextField txtDesc   = new JTextField(16);

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Nombre del platillo:")); form.add(txtNombre);
        form.add(new JLabel("Precio ($):"));           form.add(txtPrecio);
        form.add(new JLabel("Descripción:"));          form.add(txtDesc);

        int resultado = JOptionPane.showConfirmDialog(this, form,
                "Agregar nuevo platillo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado != JOptionPane.OK_OPTION) return;

        try {
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            String desc   = txtDesc.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del platillo es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            platillosActuales.add(new PlatilloDTO(nombre, precio, desc));
            refrescarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido (ej. 120.50).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Lógica: eliminar platillo ─────────────────────────────────────────────
    private void onEliminarPlatillo(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un platillo de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el platillo \"" + platillosActuales.get(fila).getNombrePlatillo() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            platillosActuales.remove(fila);
            refrescarTabla();
        }
    }

    // ── Lógica: guardar en MongoDB ────────────────────────────────────────────
    private void onGuardar() {
        try {
            MenuDTO menuActualizado = new MenuDTO(platillosActuales);
            ControlOperaciones.getInstancia().actualizarMenuRestaurante(restauranteId, menuActualizado);
            JOptionPane.showMessageDialog(this, "¡Menú guardado correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (PlatilloDTO p : platillosActuales) {
            modeloTabla.addRow(new Object[]{
                p.getNombrePlatillo(),
                String.format("$%.2f", p.getPrecio() != null ? p.getPrecio() : 0.0),
                p.getDescripcion()
            });
        }
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
