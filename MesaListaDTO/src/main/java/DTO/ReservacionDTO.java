/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author USER
 */
public class ReservacionDTO {

    private String id;
    private Integer numPersonas;
    private String folio;
    private Double costo;
    private LocalDateTime fechaHora;
    private String usuarioId;
    private String restauranteId;
    private String areaNombre;
    private List<Integer> numerosMesa;

    public ReservacionDTO() {
    }

    public ReservacionDTO(String id, Integer numPersonas, String folio, Double costo, LocalDateTime fechaHora, String usuarioId, String restauranteId, String areaNombre, List<Integer> numerosMesa) {
        this.id = id;
        this.numPersonas = numPersonas;
        this.folio = folio;
        this.costo = costo;
        this.fechaHora = fechaHora;
        this.usuarioId = usuarioId;
        this.restauranteId = restauranteId;
        this.areaNombre = areaNombre;
        this.numerosMesa = numerosMesa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(Integer numPersonas) {
        this.numPersonas = numPersonas;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(String restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getAreaNombre() {
        return areaNombre;
    }

    public void setAreaNombre(String areaNombre) {
        this.areaNombre = areaNombre;
    }

    public List<Integer> getNumerosMesa() {
        return numerosMesa;
    }

    public void setNumerosMesa(List<Integer> numerosMesa) {
        this.numerosMesa = numerosMesa;
    }
}
