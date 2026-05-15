/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.JFrame;

/**
 *
 * @author USER
 * Clase utilitaria para manejar la navegación entre pantallas.
 * Cierra la pantalla actual y abre la nueva.
 */
public class Navegador {

    public Navegador() {
    }

    public void ir(JFrame actual, JFrame destino) {
        if (actual != null) {
            actual.dispose();
        }
        destino.setVisible(true);
    }

}
