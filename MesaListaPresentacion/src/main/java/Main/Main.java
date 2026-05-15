/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Datos.GeneradorDatosPrueba;
import Pantallas.InicioSesion;
import javax.swing.SwingUtilities;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {

        GeneradorDatosPrueba.inicializarDatos();

        SwingUtilities.invokeLater(() -> {
            InicioSesion ventanaLogin = new InicioSesion();
            ventanaLogin.setVisible(true);
        });
    }

}
