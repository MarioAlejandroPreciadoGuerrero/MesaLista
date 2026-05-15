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
public class MenuDTO {

    private List<PlatilloDTO> listaPlatillos;

    public MenuDTO() {
    }

    public MenuDTO(List<PlatilloDTO> listaPlatillos) {
        this.listaPlatillos = listaPlatillos;
    }

    public List<PlatilloDTO> getListaPlatillos() {
        return listaPlatillos;
    }

    public void setListaPlatillos(List<PlatilloDTO> listaPlatillos) {
        this.listaPlatillos = listaPlatillos;
    }
}
