/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author USER
 */
public class VentaDTO {

    private String id;
    private Double montoTotal;
    private String reservacionId;
    private String usuarioId;

    public VentaDTO() {
    }

    public VentaDTO(String id, Double montoTotal, String reservacionId, String usuarioId) {
        this.id = id;
        this.montoTotal = montoTotal;
        this.reservacionId = reservacionId;
        this.usuarioId = usuarioId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(String reservacionId) {
        this.reservacionId = reservacionId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
