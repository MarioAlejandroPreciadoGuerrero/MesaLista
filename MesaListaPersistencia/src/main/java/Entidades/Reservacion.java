/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Builder.ReservacionBuilder;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class Reservacion {

    private ObjectId id;
    private Integer numPersonas;
    private String folio;
    private Double costo;
    private LocalDateTime fechaHora;
    private ObjectId usuarioId;
    private ObjectId restauranteId;
    private String areaNombre;
    private List<Integer> numerosMesa;

    /**
     * Método estático para iniciar la construcción de una Reservacion
     * utilizando el patrón Builder.
     *
     * @return ReservacionBuilder
     */
    public static ReservacionBuilder builder() {
        return new ReservacionBuilder();
    }

    /**
     * Constructor vacío requerido.
     */
    public Reservacion() {
    }

    /**
     * Constructor con todos los atributos requeridos.
     *
     * * @param id El identificador único de Mongo
     * @param numPersonas Cantidad de personas
     * @param folio Código identificador para el cliente
     * @param costo Costo de la reservación
     * @param fechaHora Fecha y hora exacta
     * @param usuarioId Referencia al documento de Usuario
     * @param restauranteId Referencia al documento de Restaurante
     * @param areaNombre Nombre del área asignada
     * @param numerosMesa Lista de números de las mesas reservadas
     */
    public Reservacion(ObjectId id, Integer numPersonas, String folio, Double costo,
            LocalDateTime fechaHora, ObjectId usuarioId, ObjectId restauranteId,
            String areaNombre, List<Integer> numerosMesa) {
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public ObjectId getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(ObjectId usuarioId) {
        this.usuarioId = usuarioId;
    }

    public ObjectId getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(ObjectId restauranteId) {
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
