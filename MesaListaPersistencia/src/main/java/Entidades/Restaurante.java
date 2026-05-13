/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class Restaurante {

    private ObjectId id;
    private String nombre;
    private Integer capacidadTotal;
    private List<Area> areas;
    private Menu menu;

    public Restaurante() {}

    public Restaurante(ObjectId id, String nombre, Integer capacidadTotal, List<Area> areas, Menu menu) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadTotal = capacidadTotal;
        this.areas = areas;
        this.menu = menu;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCapacidadTotal() { return capacidadTotal; }
    public void setCapacidadTotal(Integer capacidadTotal) { this.capacidadTotal = capacidadTotal; }
    public List<Area> getAreas() { return areas; }
    public void setAreas(List<Area> areas) { this.areas = areas; }
    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }
}
