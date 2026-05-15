/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;


import java.util.List;

/**
 *
 * @author USER
 */
public class Menu {

    private List<Platillo> listaPlatillos;

    public Menu() {}

    public Menu(List<Platillo> listaPlatillos) {
        this.listaPlatillos = listaPlatillos;
    }

    public List<Platillo> getListaPlatillos() { return listaPlatillos; }
    public void setListaPlatillos(List<Platillo> listaPlatillos) { this.listaPlatillos = listaPlatillos; }
}
