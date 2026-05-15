/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

import Entidades.Reservacion;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author USER
 */
public class ReservacionBuilder {
    private ObjectId id;
    private Integer numPersonas;
    private String folio;
    private Double costo;
    private LocalDateTime fechaHora;
    private ObjectId usuarioId;
    private ObjectId restauranteId;
    private String areaNombre;
    private List<Integer> numerosMesa;

    public ReservacionBuilder() {
    }

    public ReservacionBuilder id(ObjectId id) {
        this.id = id;
        return this;
    }

    public ReservacionBuilder numPersonas(Integer numPersonas) {
        this.numPersonas = numPersonas;
        return this;
    }

    public ReservacionBuilder folio(String folio) {
        this.folio = folio;
        return this;
    }

    public ReservacionBuilder costo(Double costo) {
        this.costo = costo;
        return this;
    }

    public ReservacionBuilder fechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public ReservacionBuilder usuarioId(ObjectId usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    public ReservacionBuilder restauranteId(ObjectId restauranteId) {
        this.restauranteId = restauranteId;
        return this;
    }

    public ReservacionBuilder areaNombre(String areaNombre) {
        this.areaNombre = areaNombre;
        return this;
    }

    public ReservacionBuilder numerosMesa(List<Integer> numerosMesa) {
        this.numerosMesa = numerosMesa;
        return this;
    }

    /**
     * Construye y devuelve una instancia de Reservacion completamente vacía,
     * ignorando cualquier atributo que se haya configurado en el builder.
     * * @return Reservacion vacía.
     */
    public Reservacion buildVacia() {
        return new Reservacion();
    }

    /**
     * Construye y devuelve una instancia de Reservacion utilizando los atributos
     * que fueron configurados previamente a través de los métodos del builder.
     * * @return Reservacion configurada (puede ser completa o parcial).
     */
    public Reservacion build() {
        return new Reservacion(
                this.id, 
                this.numPersonas, 
                this.folio, 
                this.costo, 
                this.fechaHora, 
                this.usuarioId, 
                this.restauranteId, 
                this.areaNombre, 
                this.numerosMesa
        );
    }
}
