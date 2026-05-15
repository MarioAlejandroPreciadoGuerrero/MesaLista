/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.List;

/**
 *
 * @author USER
 */
public class RestauranteDTO {

    private String id;
    private String nombre;
    private Integer capacidadTotal;
    private List<AreaDTO> areas;
    private MenuDTO menu;

    public RestauranteDTO() {
    }

    public RestauranteDTO(String id, String nombre, Integer capacidadTotal, List<AreaDTO> areas, MenuDTO menu) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadTotal = capacidadTotal;
        this.areas = areas;
        this.menu = menu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(Integer capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public List<AreaDTO> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaDTO> areas) {
        this.areas = areas;
    }

    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }
}
