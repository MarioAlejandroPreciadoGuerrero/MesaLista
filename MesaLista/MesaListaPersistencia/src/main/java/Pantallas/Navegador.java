package Pantallas;

import javax.swing.JFrame;

/**
 * Controla la navegación entre pantallas.
 * Cierra la pantalla actual y abre la siguiente.
 */
public class Navegador {

    private static JFrame pantallaActual;

    public static void ir(JFrame destino) {
        if (pantallaActual != null) pantallaActual.dispose();
        pantallaActual = destino;
        destino.setVisible(true);
    }

    public static void volver() {
        if (pantallaActual != null) pantallaActual.dispose();
    }
}
