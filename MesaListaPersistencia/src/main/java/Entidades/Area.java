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
public class Area {

    private String nombre;
    private Integer capacidadMax;
    private List<Mesa> mesas;

    public Area() {}

    public Area(String nombre, Integer capacidadMax, List<Mesa> mesas) {
        this.nombre = nombre;
        this.capacidadMax = capacidadMax;
        this.mesas = mesas;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCapacidadMax() { return capacidadMax; }
    public void setCapacidadMax(Integer capacidadMax) { this.capacidadMax = capacidadMax; }
    public List<Mesa> getMesas() { return mesas; }
    public void setMesas(List<Mesa> mesas) { this.mesas = mesas; }
}
