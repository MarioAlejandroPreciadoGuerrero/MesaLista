package Pantallas;

import Control.ControlOperaciones;
import DTO.AreaDTO;
import DTO.MesaDTO;
import DTO.RestauranteDTO;
import Utils.Navegador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdministrarAreas extends JFrame {

    private static final Color NARANJA = new Color(249, 115, 22);
    private static final Color FONDO   = new Color(250, 249, 246);
    private static final Color AZUL    = new Color(59, 130, 246);

    private final String restauranteId;
    private final String nombreRestaurante;

    private DefaultTableModel modeloTabla;
    private List<AreaDTO> areasActuales;

    public AdministrarAreas(String restauranteId, String nombreRestaurante) {
        this.restauranteId    = restauranteId;
        this.nombreRestaurante = nombreRestaurante;

        setTitle("MesaLista - Áreas de " + nombreRestaurante);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(750, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        cargarAreas();

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }

    private void cargarAreas() {
        RestauranteDTO r = ControlOperaciones.getInstancia().obtenerDetallesRestaurante(restauranteId);
        areasActuales = (r != null && r.getAreas() != null) ? new ArrayList<>(r.getAreas()) : new ArrayList<>();
    }

    // ── Header ──────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AZUL);
        header.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);

        JLabel titulo = new JLabel("Administrar Áreas");
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

        String[] columnas = {"Nombre del Área", "Capacidad Máx.", "Nº de Mesas", "Cap. por Mesa"};
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

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 215, 208)));
        body.add(scroll, BorderLayout.CENTER);

        // Botones de acción sobre la tabla
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        acciones.setOpaque(false);

        JButton btnAgregar = crearBoton("＋ Agregar Área", AZUL);
        btnAgregar.addActionListener(e -> onAgregarArea(tabla));

        JButton btnEliminar = crearBoton("✕ Eliminar seleccionada", new Color(239, 68, 68));
        btnEliminar.addActionListener(e -> onEliminarArea(tabla));

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

    // ── Lógica: agregar área ─────────────────────────────────────────────────
    private void onAgregarArea(JTable tabla) {
        JTextField txtNombre   = new JTextField(14);
        JTextField txtCapacidad = new JTextField(6);
        JTextField txtNumMesas  = new JTextField(6);
        JTextField txtCapMesa   = new JTextField(6);

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.add(new JLabel("Nombre del área:"));   form.add(txtNombre);
        form.add(new JLabel("Capacidad máxima:"));  form.add(txtCapacidad);
        form.add(new JLabel("Número de mesas:"));   form.add(txtNumMesas);
        form.add(new JLabel("Capacidad por mesa:")); form.add(txtCapMesa);

        int resultado = JOptionPane.showConfirmDialog(this, form,
                "Agregar nueva área", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado != JOptionPane.OK_OPTION) return;

        try {
            String nombre   = txtNombre.getText().trim();
            int capacidad   = Integer.parseInt(txtCapacidad.getText().trim());
            int numMesas    = Integer.parseInt(txtNumMesas.getText().trim());
            int capMesa     = Integer.parseInt(txtCapMesa.getText().trim());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del área es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<MesaDTO> mesas = new ArrayList<>();
            int numBase = areasActuales.stream()
                    .mapToInt(a -> a.getMesas() != null ? a.getMesas().size() : 0).sum() + 1;
            for (int i = 0; i < numMesas; i++) {
                mesas.add(new MesaDTO(numBase + i, capMesa));
            }

            areasActuales.add(new AreaDTO(nombre, capacidad, mesas));
            refrescarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los campos numéricos deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Lógica: eliminar área ────────────────────────────────────────────────
    private void onEliminarArea(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un área de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el área \"" + areasActuales.get(fila).getNombre() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            areasActuales.remove(fila);
            refrescarTabla();
        }
    }

    // ── Lógica: guardar en MongoDB ────────────────────────────────────────────
    private void onGuardar() {
        try {
            ControlOperaciones.getInstancia().actualizarAreasRestaurante(restauranteId, areasActuales);
            JOptionPane.showMessageDialog(this, "¡Áreas guardadas correctamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (AreaDTO area : areasActuales) {
            int numMesas = area.getMesas() != null ? area.getMesas().size() : 0;
            int capMesa  = (area.getMesas() != null && !area.getMesas().isEmpty() && area.getMesas().get(0) != null)
                    ? area.getMesas().get(0).getCapacidadMesa() : 0;
            modeloTabla.addRow(new Object[]{
                area.getNombre(),
                area.getCapacidadMax(),
                numMesas,
                capMesa
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
