/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class Venta {

    private ObjectId id;
    private Double montoTotal;
    private ObjectId reservacionId;
    private ObjectId usuarioId;

    public Venta() {}

    public Venta(ObjectId id, Double montoTotal, ObjectId reservacionId, ObjectId usuarioId) {
        this.id = id;
        this.montoTotal = montoTotal;
        this.reservacionId = reservacionId;
        this.usuarioId = usuarioId;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }
    public ObjectId getReservacionId() { return reservacionId; }
    public void setReservacionId(ObjectId reservacionId) { this.reservacionId = reservacionId; }
    public ObjectId getUsuarioId() { return usuarioId; }
    public void setUsuarioId(ObjectId usuarioId) { this.usuarioId = usuarioId; }
}
